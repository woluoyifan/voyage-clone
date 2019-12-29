package com.luoyifan.voyage.toolkit;

import com.luoyifan.voyage.constants.TacticEnum;
import com.luoyifan.voyage.entity.po.Item;
import com.luoyifan.voyage.entity.po.Ship;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserItem;
import com.luoyifan.voyage.entity.po.UserShip;
import org.apache.commons.lang3.RandomUtils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author EvanLuo
  */
public class BattleContext {
    private static final int SAILOR_POWER_RATE = 5;
    private static final String SHIP_NAME_DELIMITER = "\\ ";
    private static final String BLANK_RESULT = "无";

    private User attacker;
    private int attackerBattlePoints = 0;
    private int attackerTurn = 1;
    private int attackerRobMoney;
    private Item attackerRobItem;
    private User defender;
    private int defenderBattlePoints = 0;
    private int defenderTurn = 1;

    private List<UserShip> destroyedUserShipList = new ArrayList<>();
    private List<UserShip> robbedUserShipList = new ArrayList<>();

    private List<String> msgList = new ArrayList<>();

    private int shipNumLimit;

    public BattleContext(User attacker, User defender, int shipNumLimit) {
        this.attacker = attacker;
        this.defender = defender;
        this.shipNumLimit = shipNumLimit;
    }

    public void battle() {
        msgList.add(attacker.getUsername() + " 发动了对 " + defender.getUsername() + " 的袭击！");
        //无船只\投降\靠港\直接判定投降
        if (defender.getUserShipList().isEmpty()
                || TacticEnum.SURRENDER.equals(defender.getTactic())
                || TacticEnum.MOORING.equals(defender.getTactic())){
            quickSurrender();
            return;
        }
        //选择回避策略且具有一定商人等级时,直接投降
        if(TacticEnum.AVOID.equals(defender.getTactic()) && RandomUtils.nextInt(0, 100) < defender.getTradeLevel()){
            quickSurrender();
            return;
        }

        boolean avoidBeforeTurn = isAvoidBeforeTurn();
        if (avoidBeforeTurn) {
            msgList.add(attacker.getUsername() + " 对 " + defender.getUsername() + "的袭击被回避了！");
            return;
        }

        prepareAttackerTurn();
        prepareDefenderTurn();

        attackerTurn();
        defenderTurn();

        if (attackerBattlePoints != 0) {
            if (attackerBattlePoints >= defenderBattlePoints) {
                battleVictory();
            } else {
                battleFailure();
            }
        }

        collectResultMsg();
    }

    public String getMsg() {
        return String.join("\n", msgList);
    }

    private void quickSurrender() {
        int lostMoney = (int) (defender.getMoney() * 0.005);
        msgList.add(defender.getUsername()
                + " 投降并献上了 "
                + lostMoney
                + " G");

        attacker.setBattle(attacker.getBattle() + 100);
        attacker.setMoney(attacker.getMoney() + lostMoney);

        defender.setMoney(defender.getMoney() - lostMoney);
        defender.setBattle(defender.getBattle() - 200);
        defender.setAdventure(defender.getAdventure() - 100);
    }

    private void prepareAttackerTurn() {
        //袭击方回合数
        int numOfMyShips = attacker.getUserShipList().size();
        if (TacticEnum.WARLIKE.equals(attacker.getTactic())) {
            attackerTurn = numOfMyShips < 3 ? numOfMyShips : 3;
        } else if (TacticEnum.COMMON.equals(attacker.getTactic())) {
            attackerTurn = numOfMyShips < 2 ? numOfMyShips : 2;
        }
        //具有攻击上升宝物且船只数量大于3时，有百分之一几率增加攻击回合
        Optional<Item> attackUpItemOptional = attacker.getUserItemList()
                .stream()
                .map(UserItem::getItem)
                .filter(Item::getAttackUp)
                .findFirst();
        if (attackUpItemOptional.isPresent() && RandomUtils.nextInt(0, 100) < 1 && numOfMyShips > 3) {
            attackerTurn++;
            msgList.add("由于持有 " + attackUpItemOptional.get().getName() + " ， " + attacker.getUsername() + " 的攻击回合数+1！");
        }
    }

    private void prepareDefenderTurn() {
        switch (attacker.getTactic()) {
            case WARLIKE:
                if (TacticEnum.WARLIKE.equals(defender.getTactic())) {
                    defenderTurn = 0;
                }
                break;
            case COMMON:
                if (TacticEnum.WARLIKE.equals(defender.getTactic()) || TacticEnum.COMMON.equals(defender.getTactic())) {
                    defenderTurn = 0;
                }
                break;
            case AVOID:
                if (TacticEnum.WARLIKE.equals(defender.getTactic()) || TacticEnum.COMMON.equals(defender.getTactic()) || TacticEnum.AVOID.equals(defender.getTactic())) {
                    defenderTurn = 0;
                }
                break;
            default:
        }
    }

    private boolean isAvoidBeforeTurn() {
        //对方回避策略且存在回避战斗的宝物时回避战斗
        if (!TacticEnum.AVOID.equals(defender.getTactic())) {
            return false;
        }
        return defender.getUserItemList()
                .stream()
                .map(UserItem::getItem)
                .filter(Item::getAvoidAttack)
                .findFirst()
                .map(avoidAttackItem -> {
                    if (RandomUtils.nextInt(0, 100) >= 5) {
                        return false;
                    }
                    msgList.add(defender.getUsername()
                            + " 的 "
                            + avoidAttackItem.getName()
                            + " 发出耀眼的光芒，由于这件宝物的庇护 "
                            + defender.getUsername()
                            + " 回避了战斗，随后消失在大海上。");
                    return true;
                })
                .orElse(false);
    }

    private void attackerTurn() {
        for (int i = 0; i < attackerTurn; i++) {
            //攻击方船只为当前容量最大的船只
            UserShip attackerShip = attacker.getUserShipList()
                    .stream()
                    .sorted(Comparator.comparing(userShip -> userShip.getShip().getVolume()))
                    .collect(Collectors.toList())
                    .get(0);
            //防守方随机挑选船只进行防御
            UserShip defenderShip = defender.getUserShipList()
                    .get(RandomUtils.nextInt(0, defender.getUserShipList().size()));

            //非好战状态下，随机回避
            if (!TacticEnum.WARLIKE.equals(defender.getTactic())) {
                //回避受策略、航海力、船速影响
                double avoid = (double) (defender.getCommand()) / (TacticEnum.AVOID.equals(defender.getTactic()) ? 3 : 5);
                avoid = avoid * (defenderShip.getSpeed().doubleValue() + defender.getNavigation() * 0.01) / 7;
                if (RandomUtils.nextDouble(0, 100) < avoid) {
                    msgList.add("对 " + defender.getUsername() + " 的 " + defenderShip.getShip().getName() + " 攻击失误了！");
                    continue;
                }
            }

            //计算攻击力
            int attackerSailorPower = attacker.getTotalVolume() == 0 ?
                    0 : sailorPower(attacker.getSailor(), attackerShip.getVolume(), attacker.getTotalVolume());
            int defenderSailorPower = defender.getTotalVolume() == 0 ?
                    0 : sailorPower(defender.getSailor(), defenderShip.getVolume(), defender.getTotalVolume());
            int attackerPower = power(attacker.getAttack(), attackerSailorPower);
            int defenderPower = power(defender.getAttack(), defenderSailorPower);

            List<Item> opponentShieldItemList = defender.getUserItemList()
                    .stream()
                    .map(UserItem::getItem)
                    .filter(Item::getShield)
                    .collect(Collectors.toList());
            //防御成功可能,此处对比原版有所更改,原版为rand(100) < 10 - 2 * $sh_exist,但$sh_exist变量要么为-1要么为0,改为(10+2x防御属性宝物数量)/100
            if (opponentShieldItemList.size() > 0
                    && RandomUtils.nextInt(0, 100) < (10 + 2 * opponentShieldItemList.size())) {
                String itemNameLine = opponentShieldItemList.stream()
                        .map(Item::getName)
                        .collect(Collectors.joining(","));
                msgList.add(defender.getUsername() + " 的 " + itemNameLine + " 将攻击吸收了！" + attacker.getUsername() + " 攻击力下降！");
                attackerPower = (int) (attackerPower * 0.9);
            }
            attackerBattlePoints += attackerPower;
            //发起方先进行攻击
            attack(attacker, attackerPower, defender, defenderSailorPower, defenderShip);
            //防守方剩余船只判定
            if (defender.getUserShipList().isEmpty()) {
                defenderBattlePoints = 0;
                break;
            }
            //防守方反击计算
            if (RandomUtils.nextInt(0, 200) < defender.getAdventureLevel()) {
                defenderPower += defender.getAdventureLevel();
            }
            if (RandomUtils.nextInt(0, 200) < attacker.getBattleLevel()) {
                defenderPower = (int) (defenderPower * 0.9);
                msgList.add(attacker.getUsername() + " 丰富的战斗经验使 " + defender.getUsername() + " 畏惧了！");
            }
            defenderBattlePoints += defenderPower;
            //防守方发动反击
            attack(defender, defenderPower, attacker, attackerSailorPower, attackerShip);
            //攻击方剩余船只判定
            if (attacker.getUserShipList().isEmpty()) {
                attackerBattlePoints = 0;
                break;
            }
            //防守方回避剩余攻击
            if (TacticEnum.AVOID.equals(defender.getTactic()) && RandomUtils.nextInt(0, 100) < defender.getCommand() * 0.5) {
                break;
            }
        }

    }

    private void defenderTurn() {
        for (int i = 0; i < defenderTurn; i++) {
            if (defender.getUserShipList().isEmpty() || attacker.getUserShipList().isEmpty()) {
                break;
            }
            //随机挑选双方船只
            UserShip myShip = attacker.getUserShipList()
                    .get(RandomUtils.nextInt(0, attacker.getUserShipList().size()));
            //防守方随机挑选船只进行防御
            UserShip opponentShip = defender.getUserShipList()
                    .get(RandomUtils.nextInt(0, defender.getUserShipList().size()));
            //计算攻击力
            int attackerSailorPower = attacker.getTotalVolume() == 0 ?
                    0 : sailorPower(attacker.getSailor(), myShip.getVolume(), attacker.getTotalVolume());
            int defenderSailorPower = defender.getTotalVolume() == 0 ?
                    0 : sailorPower(defender.getSailor(), opponentShip.getVolume(), defender.getTotalVolume());
            int attackerPower = power(attacker.getAttack(), attackerSailorPower);
            int defenderPower = power(defender.getAttack(), defenderSailorPower);

            defenderBattlePoints += defenderPower;
            //防守方发动攻击
            attack(defender, defenderPower, attacker, attackerSailorPower, myShip);
        }
    }

    private void battleVictory() {
        int battleBalance = defender.getBattle() - attacker.getBattle();
        int battleUp = 3000;
        int adventureUp = 0;
        if (battleBalance > 0) {
            battleUp += battleBalance * 0.5;
            adventureUp = (int) (battleBalance * 0.01);
        }
        attacker.setBattle(attacker.getBattle() + battleUp);
        attacker.setBattleExp(attacker.getBattleExp() + battleUp);
        attacker.setAdventure(attacker.getAdventure() + adventureUp);
        attacker.setAdventureExp(attacker.getAdventureExp() + adventureUp);
        defender.setBattle(defender.getBattle() - 200);
        defender.setTrade(defender.getTrade() - 200);

        //财宝掠夺可能
        robItem();

        msgList.add(attacker.getUsername() + " 胜利了！" + defender.getUsername() + " 不敌败逃！");

        robMoney();
    }

    private void battleFailure() {
        int battleUp = 2000;
        if (attacker.getBattle() > defender.getBattle()) {
            battleUp += attacker.getBattle() / 10;
        }
        defender.setBattle(defender.getBattle() + battleUp);
        defender.setBattleExp(defender.getBattle() + battleUp);
        attacker.setBattle((int) (attacker.getBattle() * 0.9));
        attacker.setTrade((int) (attacker.getTrade() * 0.9));

        msgList.add(defender.getUsername() + " 击退了 " + attacker.getUsername() + " ！");
    }

    private void robMoney() {
        if (RandomUtils.nextInt(0, 100) > defender.getCommand() / 4
                && defender.getMoney() > 0) {
            int lostMoney = (int) (defender.getMoney() * RandomUtils.nextDouble(0, 0.05));
            attackerRobMoney = lostMoney;
            attacker.setMoney(attacker.getMoney() + lostMoney);
            defender.setMoney(defender.getMoney() - lostMoney);

            msgList.add(attacker.getUsername() + " 掠夺了 " + defender.getUsername() + " " + lostMoney + " G！");
        }
    }

    private void robItem() {
        List<UserItem> defenderItemList = defender.getUserItemList();
        List<UserItem> attackerItemList = attacker.getUserItemList();
        if (defenderItemList.isEmpty()
                || RandomUtils.nextInt(0, 100) < defender.getCommand() / 4) {
            return;
        }
        List<Item> itemProtectItemList = defenderItemList.stream()
                .map(UserItem::getItem)
                .filter(Item::getItemProtect)
                .collect(Collectors.toList());
        //一定几率保护财宝
        if (itemProtectItemList.size() > 0 && RandomUtils.nextInt(0, 100) > 5) {
            msgList.add("由于 " + itemProtectItemList.get(0).getName() + " 的庇护，" + defender.getUsername() + " 把财宝守护住了！");
            return;
        }
        UserItem targetUserItem = defenderItemList.get(RandomUtils.nextInt(0, defenderItemList.size()));
        boolean isRepeat = attackerItemList
                .stream()
                .anyMatch(attackerUserItem -> attackerUserItem.getItem().getId().equals(targetUserItem.getItem().getId()));
        if (isRepeat) {
            return;
        }

        defenderItemList.remove(targetUserItem);
        targetUserItem.setUser(attacker);
        attackerItemList.add(targetUserItem);

        attackerRobItem = targetUserItem.getItem();
    }

    /**
     * 具体的战斗
     * 该方法中的攻击方和防御方不等同于成员变量中的攻击方和防御方
     *
     * @param attacker
     * @param attackerPower
     * @param defender
     * @param defenderSailorPower
     * @param defenderShip
     */
    private void attack(User attacker, int attackerPower, User defender, int defenderSailorPower, UserShip defenderShip) {
        List<UserShip> attackerUserShipList = attacker.getUserShipList();
        List<UserShip> defenderUserShipList = defender.getUserShipList();
        //总战力不敌对手水手战力
        if (attackerPower - defenderSailorPower / SAILOR_POWER_RATE < 0) {
            //对手水手减少
            defender.setSailor(defender.getSailor() - 5 * attackerPower);
        } else {
            //对手水手减少
            defender.setSailor(defender.getSailor() - defenderSailorPower);
            if (defender.getSailor() > 0) {
                defender.setSailor(defender.getSailor() - 1);
            }
            //我方战斗力抵消
            attackerPower -= defenderSailorPower / SAILOR_POWER_RATE;
            String attackMsg = attacker.getUsername() + " 攻击 " + defender.getUsername() + " 的 " + defenderShip.getShip().getName()
                    + " 造成了 " + attackerPower + " 点的伤害";
            //对对手船只造成伤害
            if (defenderShip.getHp() - attackerPower > 0) {
                defenderShip.setHp(defenderShip.getHp() - attackerPower);
                msgList.add(attackMsg + "！");
            } else {
                //物资保留率
                defender.removeUserShip(defenderShip);
                //船只数量未满，进行夺取
                if (attackerUserShipList.size() < shipNumLimit) {
                    attacker.addUserShip(defenderShip);
                    robbedUserShipList.add(defenderShip);
                    msgList.add(attackMsg + "，将其 夺取 了！");
                } else {
                    //数量已满，直接击沉
                    destroyedUserShipList.add(defenderShip);
                    msgList.add(attackMsg + "，将其 击沉 了！");
                }
            }
        }
    }

    private String buildOwnShipName(List<UserShip> userShipList, Long owner) {
        return userShipList.stream()
                .filter(userShip -> userShip.getUser().getId().equals(owner))
                .map(UserShip::getShip)
                .map(Ship::getName)
                .collect(Collectors.joining(SHIP_NAME_DELIMITER));
    }

    private void collectResultMsg() {

        String attackerRobbedShipNames = buildOwnShipName(robbedUserShipList, attacker.getId());
        if (attackerRobbedShipNames.isEmpty()) {
            attackerRobbedShipNames = BLANK_RESULT;
        }
        //被击沉的船只不会更改所属
        String attackerDestroyedShipNames =buildOwnShipName(robbedUserShipList, defender.getId());
        if (attackerDestroyedShipNames.isEmpty()) {
            attackerDestroyedShipNames = BLANK_RESULT;
        }
        String defenderRobbedShipNames =buildOwnShipName(robbedUserShipList, defender.getId());
        if (defenderRobbedShipNames.isEmpty()) {
            defenderRobbedShipNames = BLANK_RESULT;
        }
        //被击沉的船只不会更改所属
        String defenderDestroyedShipNames =buildOwnShipName(destroyedUserShipList, attacker.getId());
        if (defenderDestroyedShipNames.isEmpty()) {
            defenderDestroyedShipNames = BLANK_RESULT;
        }
        String robbedItemName = attackerRobItem == null ? BLANK_RESULT : attackerRobItem.getName();
        msgList.add("战斗结果：");
        msgList.add(attacker.getUsername() + " [夺取：" + attackerRobbedShipNames + ",击沉：" + attackerDestroyedShipNames
                + ", 获得资金：" + attackerRobMoney + ", 获得财宝：" + robbedItemName
                + ", 被夺取：" + defenderRobbedShipNames + ", 被击沉：" + defenderDestroyedShipNames + "]");
        msgList.add(defender.getUsername() + " [夺取：" + defenderRobbedShipNames + ",击沉：" + defenderDestroyedShipNames
                + ", 失去资金：" + attackerRobMoney + ", 失去财宝：" + robbedItemName
                + ", 被夺取：" + attackerRobbedShipNames + ", 被击沉：" + attackerDestroyedShipNames + "]");
    }

    /**
     * 水手战斗力
     *
     * @param sailor       水手数量
     * @param targetVolume 容积最大的船只的容积
     * @param totalVolume  总容积
     */
    private int sailorPower(int sailor, int targetVolume, int totalVolume) {
        return sailor * targetVolume / totalVolume;
    }

    /**
     * 总战斗力
     *
     * @param attack      战斗力
     * @param sailorPower 水手战斗力
     */
    private int power(int attack, int sailorPower) {
        return RandomUtils.nextInt(0, 2 * attack) + sailorPower / SAILOR_POWER_RATE;
    }
}
