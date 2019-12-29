package com.luoyifan.voyage.service.impl;

import com.luoyifan.voyage.constants.CityOperationEnum;
import com.luoyifan.voyage.constants.UserOperationEnum;
import com.luoyifan.voyage.dao.ShipRepository;
import com.luoyifan.voyage.entity.UserDetails;
import com.luoyifan.voyage.entity.po.Goods;
import com.luoyifan.voyage.entity.po.Port;
import com.luoyifan.voyage.entity.po.Ship;
import com.luoyifan.voyage.entity.po.User;
import com.luoyifan.voyage.entity.po.UserGoods;
import com.luoyifan.voyage.entity.po.UserItem;
import com.luoyifan.voyage.entity.po.UserPortCity;
import com.luoyifan.voyage.entity.po.UserPortCityDeposit;
import com.luoyifan.voyage.entity.po.UserPortCityGoods;
import com.luoyifan.voyage.entity.po.UserPortCityItem;
import com.luoyifan.voyage.entity.po.UserPortCityShip;
import com.luoyifan.voyage.entity.po.UserShip;
import com.luoyifan.voyage.property.VoyageProperty;
import com.luoyifan.voyage.service.CityService;
import com.luoyifan.voyage.service.EventService;
import com.luoyifan.voyage.service.UserService;
import com.luoyifan.voyage.util.AssertUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author EvanLuo
  */
@Transactional(rollbackFor = {Exception.class, RuntimeException.class})
@Service
public class CityServiceImpl implements CityService {
    @Autowired
    private VoyageProperty voyageProperty;
    @Autowired
    private UserService userService;
    @Autowired
    private EventService eventService;
    @Autowired
    private ShipRepository shipRepository;
    @Autowired
    private EntityManager entityManager;

    /**
     * 获取建立城镇的价格
     */
    @Override
    public int getPriceOfCreateCity() {
        User user = userService.getCurrent();
        int price = voyageProperty.getCityCreatePrice();
        if (user.getMoney() >= voyageProperty.getCityCreatePriceRichThreshold()) {
            price = (int) (user.getMoney() * voyageProperty.getCityCreatePriceRichRate());
        }
        return price;
    }

    /**
     * 列出可用的操作
     */
    @Override
    public List<CityOperationEnum> listCityOperation() {
        User user = userService.getCurrent();
        checkCity(user);
        List<CityOperationEnum> baseOperationList = Stream.of(CityOperationEnum.TRADE, CityOperationEnum.BANK, CityOperationEnum.ATTACK)
                .collect(Collectors.toList());
        if (isOwnCurrentCity(user)) {
            baseOperationList.addAll(Arrays.asList(CityOperationEnum.TAX_RATE, CityOperationEnum.ADMIN, CityOperationEnum.DESCRIPTION, CityOperationEnum.NAME));
        }
        return baseOperationList;
    }

    /**
     * 建立城镇
     */
    @Override
    public void createCity(String name) {
        User user = userService.getCurrent();
        Port port = user.getPort();
        AssertUtils.isTrue(port != null, "在港口中才能建立城镇");
        AssertUtils.isTrue(port.getUserPortCity() == null, "城镇已存在");
        int price = getPriceOfCreateCity();
        AssertUtils.isTrue(user.getMoney() >= price, "金额不足");
        user.setMoney(user.getMoney() - price);
        UserPortCity userPortCity = new UserPortCity();
        userPortCity.setUser(user);
        userPortCity.setPort(port);
        userPortCity.setName(name);
        userPortCity.setDescription("欢迎光临");
        userPortCity.setHp(voyageProperty.getCityInitHp());
        userPortCity.setMoney(0);
        userPortCity.setTaxRate(0);
        userPortCity.setPrice(0);
        userPortCity.setUserPortCityGoodsList(null);
        userPortCity.setUserPortCityItemList(null);
        userPortCity.setUserPortCityShipList(null);
        user.getUserPortCityList().add(userPortCity);
        userService.save(user);

        //刷新关联
        entityManager.refresh(user.getPort());

        String msg = "花费 " + price + "G 在 " + port.getName() + " 中建立了城镇 " + userPortCity.getName();
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }


    /**
     * 存储商品
     */
    @Override
    public void storeGoodsToCity(Long userGoodsId, Integer quantity, Integer price) {
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        List<UserPortCityGoods> userPortCityGoodsList = userPortCity.getUserPortCityGoodsList();
        AssertUtils.isTrue(userPortCityGoodsList.size() < voyageProperty.getCityGoodsLimit(), "最多存放" + voyageProperty.getCityGoodsLimit() + "格商品");
        List<UserGoods> userGoodsList = user.getUserGoodsList();
        userGoodsList.stream()
                .filter(userGoods -> userGoods.getId().equals(userGoodsId))
                .findFirst()
                .ifPresent(userGoods -> {
                    UserPortCityGoods userPortCityGoods = new UserPortCityGoods();
                    userPortCityGoods.setUserPortCity(userPortCity);
                    userPortCityGoods.setGoods(userGoods.getGoods());
                    userPortCityGoods.setPrice(price);
                    int realQuantity = quantity;
                    if (userGoods.getQuantity() <= quantity) {
                        realQuantity = userGoods.getQuantity();
                        userGoodsList.remove(userGoods);
                    } else {
                        userGoods.setQuantity(userGoods.getQuantity() - realQuantity);
                    }
                    userPortCityGoods.setQuantity(realQuantity);
                    userPortCityGoodsList.add(userPortCityGoods);
                    userService.save(user);

                    String msg = "将 " + realQuantity + " 个 " + userGoods.getGoods().getName() + " 以 " + price + "G 售价放入位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() +
                            " 中";
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.GOODS, msg);
                });
    }

    /**
     * 存储船只
     */
    @Override
    public void storeShipToCity(Long userShipId, Integer price) {
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        List<UserPortCityShip> userPortCityShipList = userPortCity.getUserPortCityShipList();
        AssertUtils.isTrue(userPortCityShipList.size() < voyageProperty.getCityShipLimit(), "最多存放" + voyageProperty.getCityShipLimit() + "条船只");
        List<UserShip> userShipList = user.getUserShipList();
        userShipList.stream()
                .filter(userShip -> userShip.getId().equals(userShipId))
                .findFirst()
                .ifPresent(userShip -> {
                    UserPortCityShip userPortCityShip = new UserPortCityShip();
                    userPortCityShip.setUserPortCity(userPortCity);
                    userPortCityShip.setShip(userShip.getShip());
                    userPortCityShip.setVolume(userShip.getVolume());
                    userPortCityShip.setHp(userShip.getHp());
                    userPortCityShip.setSpeed(userShip.getSpeed());
                    userPortCityShip.setPrice(price);

                    userShipList.remove(userShip);
                    userPortCityShipList.add(userPortCityShip);
                    userService.save(user);

                    String msg = "将 " + userShip.getShip().getName() + " 以 " + price + "G 售价放入位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 中";
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    /**
     * 存储物品
     */
    @Override
    public void storeItemToCity(Long userItemId, Integer price) {
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        List<UserItem> userItemList = user.getUserItemList();
        userItemList.stream()
                .filter(userItem -> userItem.getId().equals(userItemId))
                .findFirst()
                .ifPresent(userItem -> {
                    UserPortCityItem userPortCityItem = new UserPortCityItem();
                    userPortCityItem.setUserPortCity(userPortCity);
                    userPortCityItem.setItem(userItem.getItem());
                    userPortCityItem.setPrice(price);
                    userItemList.remove(userItem);
                    userPortCity.getUserPortCityItemList().add(userPortCityItem);
                    userService.save(user);

                    String msg = "将 " + userItem.getItem().getName() + " 以 " + price + "G 售价放入位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 中";
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    /**
     * 修改城镇名称
     */
    @Override
    public void updateCityName(String name) {
        AssertUtils.isTrue(name.length() < 10, "描述必须在10字以内");
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        userPortCity.setName(name);
        userService.save(user);

        String msg = "将位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 的名称修改为 [" + name + "]";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 修改城镇描述
     */
    @Override
    public void updateCityDescription(String description) {
        AssertUtils.isTrue(description.length() < 64, "描述必须在64字以内");
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        userPortCity.setDescription(description);
        userService.save(user);

        String msg = "将位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 的描述修改为 [" + description + "]";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 修改城镇税率
     */
    @Override
    public void updateCityTaxRate(Integer rate) {
        AssertUtils.isTrue(rate >= 0 && rate <= 99, "税率不合理");
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        userPortCity.setTaxRate(rate);
        userService.save(user);

        String msg = "将位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 的税率修改为 " + rate + "%";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 修复城镇每hp花费
     */
    @Override
    public int getRepairCityCost() {
        return voyageProperty.getCityRepairCost();
    }

    /**
     * 城镇最大hp
     */
    @Override
    public int getCityMaxHp() {
        return voyageProperty.getCityInitHp();
    }

    /**
     * 修复城镇
     */
    @Override
    public void repairCity(Integer hp) {
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        Integer maxHp = getCityMaxHp();
        int repairableHp = maxHp - userPortCity.getHp();
        AssertUtils.isTrue(repairableHp > 0, "不需要修复");
        int repairHp = repairableHp > hp ? hp : repairableHp;
        int price = repairHp * getRepairCityCost();
        user.setMoney(user.getMoney() - price);
        AssertUtils.isTrue(user.getMoney() >= 0, "资金不足");
        userPortCity.setHp(userPortCity.getHp() + repairHp);
        userService.save(user);

        String msg = "花费 " + price + "G 将位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 修复了 " + repairHp + "HP";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 取出城镇盈余
     */
    @Override
    public void withdrawalCityMoney(Integer money) {
        if (money <= 0) {
            return;
        }
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        if (userPortCity.getMoney() <= 0) {
            return;
        }
        int realMoney = money;
        if (userPortCity.getMoney() < money) {
            realMoney = userPortCity.getMoney();
        }
        user.setMoney(user.getMoney() + realMoney);
        userPortCity.setMoney(userPortCity.getMoney() - realMoney);

        userService.save(user);

        String msg = "从位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 中取出 " + realMoney + "G";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 出售城镇
     */
    @Override
    public void sellCity(Integer price) {
        User user = userService.getCurrent();
        checkOwnCurrentCity(user);
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        userPortCity.setPrice(price < 0 ? 0 : price);
        userService.save(user);

        String msg = "以 " + price + "G 的价格设定出售位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName();
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 获取可购买的商品
     */
    @Override
    public List<UserPortCityGoods> listPurchasableGoods() {
        User user = userService.getCurrent();
        checkCity(user);
        return user.getPort().getUserPortCity().getUserPortCityGoodsList();
    }

    /**
     * 购买商品
     */
    @Override
    public void purchaseItem(Long userPortCityItemId) {
        listPurchasableItem()
                .stream()
                .filter(userPortCityItem -> userPortCityItem.getId().equals(userPortCityItemId))
                .findFirst()
                .ifPresent(userPortCityItem -> {
                    User user = userService.getCurrent();
                    int realPrice = isOwnCurrentCity(user) ? 0 : userPortCityItem.getPrice();
                    AssertUtils.isTrue(realPrice <= user.getMoney(), "资金不足");
                    UserItem userItem = new UserItem();
                    userItem.setUser(user);
                    userItem.setItem(userPortCityItem.getItem());
                    user.getUserItemList().add(userItem);
                    user.setMoney(user.getMoney() - realPrice);

                    UserPortCity userPortCity = userPortCityItem.getUserPortCity();
                    userPortCity.getUserPortCityItemList().remove(userPortCityItem);
                    userPortCity.setMoney(userPortCity.getMoney() + realPrice);

                    userService.save(user);

                    String msg = "以 " + userPortCityItem.getPrice() + "G 买入 " + userPortCityItem.getItem().getName();
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    /**
     * 获取可购买的船只
     */
    @Override
    public List<UserPortCityShip> listPurchasableShip() {
        User user = userService.getCurrent();
        checkCity(user);
        return user.getPort().getUserPortCity().getUserPortCityShipList();
    }

    /**
     * 购买船只
     */
    @Override
    public void purchaseShip(Long userPortCityShipId) {
        listPurchasableShip()
                .stream()
                .filter(userPortCityShip -> userPortCityShip.getId().equals(userPortCityShipId))
                .findFirst()
                .ifPresent(userPortCityShip -> {
                    User user = userService.getCurrent();
                    int realPrice = isOwnCurrentCity(user) ? 0 : userPortCityShip.getPrice();
                    AssertUtils.isTrue(realPrice <= user.getMoney(), "资金不足");

                    UserShip userShip = new UserShip();
                    userShip.setShip(userPortCityShip.getShip());
                    userShip.setVolume(userPortCityShip.getVolume());
                    userShip.setHp(userPortCityShip.getHp());
                    userShip.setSpeed(userPortCityShip.getSpeed());
                    user.addUserShip(userShip);
                    user.setMoney(user.getMoney() - realPrice);

                    UserPortCity userPortCity = userPortCityShip.getUserPortCity();
                    userPortCity.getUserPortCityShipList().remove(userPortCityShip);
                    userPortCity.setMoney(userPortCity.getMoney() + realPrice);

                    userService.save(user);

                    String msg = "以 " + userPortCityShip.getPrice() + "G 买入 " + userPortCityShip.getShip().getName();
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    /**
     * 获取可购买的秘宝船只
     */
    @Override
    public List<Ship> listPurchasableSecretShip() {
        User user = userService.getCurrent();
        checkCity(user);
        return shipRepository.findAll()
                .stream()
                .filter(Ship::getSecret)
                .filter(ship -> user.getPort().getUserPortCity().getMoney()>ship.getSecretMoney())
                .collect(Collectors.toList());
    }

    /**
     * 购买秘宝船只
     */
    @Override
    public void purchaseSecrtShip(Long shipId) {
        listPurchasableSecretShip()
                .stream()
                .filter(ship->ship.getId().equals(shipId))
                .findFirst()
                .ifPresent(ship->{
                    User user = userService.getCurrent();
                    AssertUtils.isTrue(ship.getPrice() <= user.getMoney(), "资金不足");
                    UserShip userShip = new UserShip();
                    userShip.setShip(ship);
                    userShip.setVolume(ship.getVolume());
                    userShip.setHp(ship.getHp());
                    userShip.setSpeed(ship.getSpeed());

                    user.addUserShip(userShip);
                    user.setMoney(user.getMoney() - ship.getPrice());

                    userService.save(user);

                    String msg = "以 " + ship.getPrice() + "G 买入 " + ship.getName();
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.SHIP, msg);
                });
    }

    /**
     * 获取可购买的物品
     */
    @Override
    public List<UserPortCityItem> listPurchasableItem() {
        User user = userService.getCurrent();
        checkCity(user);
        return user.getPort().getUserPortCity().getUserPortCityItemList();
    }

    /**
     * 购买物品
     */
    @Override
    public void purchaseGoods(Long userPortCityGoodsId, int quantity) {
        listPurchasableGoods()
                .stream()
                .filter(userPortCityGoods -> userPortCityGoods.getId().equals(userPortCityGoodsId))
                .findFirst()
                .ifPresent(userPortCityGoods -> {
                    User user = userService.getCurrent();

                    UserPortCity userPortCity = userPortCityGoods.getUserPortCity();
                    int realQuantity;
                    if (userPortCityGoods.getQuantity() > quantity) {
                        realQuantity = quantity;
                        userPortCityGoods.setQuantity(userPortCityGoods.getQuantity() - quantity);
                    } else {
                        realQuantity = userPortCityGoods.getQuantity();
                        userPortCity.getUserPortCityGoodsList().remove(userPortCityGoods);
                    }
                    int realPrice = isOwnCurrentCity(user) ? 0 : userPortCityGoods.getPrice();
                    AssertUtils.isTrue(realPrice * realQuantity <= user.getMoney(), "资金不足");
                    userPortCity.setMoney(userPortCity.getMoney() + realPrice * realQuantity);

                    Goods goods = userPortCityGoods.getGoods();
                    UserGoods userGoods = user.getUserGoodsList()
                            .stream()
                            .filter(entity -> entity.getGoods().getId().equals(goods.getId()))
                            .findFirst()
                            .orElseGet(() -> {
                                UserGoods entity = new UserGoods();
                                entity.setUser(user);
                                entity.setGoods(goods);
                                entity.setQuantity(0);
                                user.getUserGoodsList().add(entity);
                                return entity;
                            });

                    userGoods.setQuantity(userGoods.getQuantity() + realQuantity);
                    user.setMoney(user.getMoney() - realPrice * realQuantity);
                    userService.save(user);

                    String msg = "以单价 " + userPortCityGoods.getPrice() + "G 买入 " + goods.getName() + " " + quantity + " 个";
                    UserDetails.setMsg(msg);
                    userService.saveCurrentUserOperation(UserOperationEnum.GOODS, msg);
                });
    }

    /**
     * 获取当前所在城镇的存款
     */
    @Override
    public int getCurrentPortDepositAmount() {
        User user = userService.getCurrent();
        checkCity(user);
        return getCurrentCityDeposit(user)
                .map(UserPortCityDeposit::getAmount)
                .orElse(0);
    }

    /**
     * 城镇银行存款
     */
    @Override
    public void deposit(Integer amount) {
        if (amount <= 0) {
            return;
        }
        User user = userService.getCurrent();
        checkCity(user);
        UserPortCityDeposit currentCityDeposit = getCurrentCityDeposit(user)
                .orElseGet(() -> {
                    UserPortCityDeposit userPortCityDeposit = new UserPortCityDeposit();
                    userPortCityDeposit.setUser(user);
                    userPortCityDeposit.setUserPortCity(user.getPort().getUserPortCity());
                    userPortCityDeposit.setAmount(0);
                    user.getUserPortCityDepositList().add(userPortCityDeposit);
                    return userPortCityDeposit;
                });
        int realAmount = amount;
        if (user.getMoney() < amount) {
            realAmount = user.getMoney();
        }
        currentCityDeposit.setAmount(currentCityDeposit.getAmount() + realAmount);
        user.setMoney(user.getMoney() - realAmount);
        userService.save(user);

        String msg = "在位于 " + currentCityDeposit.getUserPortCity().getPort().getName() + " 的城镇 " + currentCityDeposit.getUserPortCity().getName() + " 中存入 " + realAmount + "G";
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
    }

    /**
     * 城镇银行取款
     */
    @Override
    public void withdraw(Integer amount) {
        if (amount <= 0) {
            return;
        }
        User user = userService.getCurrent();
        checkCity(user);
        getCurrentCityDeposit(user).ifPresent(currentCityDeposit -> {
            int realAmount = amount;
            if (currentCityDeposit.getAmount() < amount) {
                realAmount = currentCityDeposit.getAmount();
                user.getUserPortCityDepositList().remove(currentCityDeposit);
            } else {
                currentCityDeposit.setAmount(currentCityDeposit.getAmount() - amount);
            }
            UserPortCity userPortCity = currentCityDeposit.getUserPortCity();
            int rateAmount = 0;
            if (!isOwnCurrentCity(user)) {
                rateAmount = realAmount * userPortCity.getTaxRate() / 100;
                userPortCity.setMoney(userPortCity.getMoney() + rateAmount);
                realAmount -= rateAmount;
            }
            user.setMoney(user.getMoney() + realAmount);
            userService.save(user);

            String msg = "从位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 中取出 " + realAmount + "G ，缴纳了 " + rateAmount + "G 税金";
            UserDetails.setMsg(msg);
            userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
        });
    }

    @Override
    public void armyAttack() {
        User user = userService.getCurrent();
        user.setBattleTime(LocalDateTime.now());
        checkCity(user);

        List<UserShip> userShipList = user.getUserShipList();
        AssertUtils.isTrue(userShipList.size() > 0, "没有船舰无法战斗");
        checkBattleLimit(user);

        int myPower = user.getAttack() + user.getSailor() / 10 + RandomUtils.nextInt(0, voyageProperty.getCityAttackRandom()) - (voyageProperty.getCityAttackRandom() / 2);
        if (myPower < 0) {
            myPower = 0;
        }
        UserPortCity userPortCity = user.getPort().getUserPortCity();
        userPortCity.setHp(userPortCity.getHp() - myPower);

        int cityPower = voyageProperty.getCityAttackPower() + RandomUtils.nextInt(0, voyageProperty.getCityAttackPower()) - voyageProperty.getCityAttackPower() / 2;
        UserShip targetUserShip = userShipList.get(RandomUtils.nextInt(0, userShipList.size()));
        targetUserShip.setHp(targetUserShip.getHp() - cityPower);

        String msg = "攻击位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + "！";
        if (targetUserShip.getHp() <= 0) {
            user.removeUserShip(targetUserShip);
            msg += targetUserShip.getShip().getName() + " 被击沉了！";
        }
        if (userPortCity.getHp() > 0) {
            msg += "攻击位于 " + userPortCity.getPort().getName() + " 的城镇 " + userPortCity.getName() + " 造成了 " + myPower + " 点伤害！";
            hurt(user, msg);
            return;
        }
        destroy(user);
    }

    @Override
    public void moneyAttack() {
        User user = userService.getCurrent();
        user.setBattleTime(LocalDateTime.now());
        checkCity(user);
        int cityMoneyAttackCost = getCityMoneyAttackCost();
        int cityMoneyAttackDamage = getCityMoneyAttackDamage();

        AssertUtils.isTrue(user.getMoney() >= cityMoneyAttackCost, "资金不足");
        checkBattleLimit(user);

        user.setMoney(user.getMoney() - cityMoneyAttackCost);
        UserPortCity userPortCity = user.getPort()
                .getUserPortCity();
        userPortCity.setHp(userPortCity.getHp() - cityMoneyAttackDamage);
        if (userPortCity.getHp() > 0) {
            hurt(user, "给予城镇 " + userPortCity.getName() + " " + cityMoneyAttackDamage + " 点的伤害！");
            return;
        }
        destroy(user);
    }

    @Override
    public int getCityMoneyAttackCost() {
        return voyageProperty.getCityMoneyAttackCost();
    }

    @Override
    public int getCityMoneyAttackDamage() {
        return voyageProperty.getCityMoneyAttackDamage();
    }

    private void hurt(User attacker, String msg) {
        UserDetails.setMsg(msg);
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);
        userService.save(attacker);
        userService.save(attacker.getPort().getUserPortCity().getUser());
    }

    private void destroy(User attacker) {
        UserPortCity targetCity = attacker.getPort()
                .getUserPortCity();
        String msg = "让 " + targetCity.getName() + " 毁灭了！";
        UserDetails.setMsg(msg + "\n战斗名声大幅上升！商人名声大幅下降！");
        userService.saveCurrentUserOperation(UserOperationEnum.CITY, msg);

        attacker.setBattle(attacker.getBattle() + 5000);
        Integer trade = attacker.getTrade();
        if (trade > 10000) {
            trade = trade / 2;
        }
        trade -= 5000;
        attacker.setTrade(trade);

        userService.save(attacker);
        User cityOwner = targetCity.getUser();
        cityOwner.getUserPortCityList()
                .remove(targetCity);
        userService.save(cityOwner);
        eventService.create(attacker.getUsername() + " 攻下了 " + cityOwner.getUsername() + " 的 " + targetCity.getName());
    }

    private void checkBattleLimit(User user) {
        long combatSeconds = user.getBattleTime().until(LocalDateTime.now(), ChronoUnit.SECONDS);
        Integer combatLimit = voyageProperty.getCityCombatLimit();
        AssertUtils.isTrue(combatSeconds > combatLimit, "城市战斗间隔必须大于" + combatLimit / 60 + "分钟");
    }

    private Optional<UserPortCityDeposit> getCurrentCityDeposit(User user) {
        return user.getUserPortCityDepositList()
                .stream()
                .filter(userPortCityDeposit -> userPortCityDeposit.getUserPortCity().getId().equals(user.getPort().getUserPortCity().getId()))
                .findFirst();
    }

    private void checkCity(User user) {
        AssertUtils.isTrue(user.getPort() != null && user.getPort().getUserPortCity() != null, "在城镇中才能进行这项操作");
    }

    private void checkOwnCurrentCity(User user) {
        AssertUtils.isTrue(isOwnCurrentCity(user), "不是自己拥有的城镇");
    }

    private boolean isOwnCurrentCity(User user) {
        return user.getPort().getUserPortCity().getUser().getId().equals(user.getId());
    }

}
