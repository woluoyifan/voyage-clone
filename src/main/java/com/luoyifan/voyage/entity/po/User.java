package com.luoyifan.voyage.entity.po;

import com.luoyifan.voyage.constants.PointEnum;
import com.luoyifan.voyage.constants.TacticEnum;
import com.luoyifan.voyage.util.CalculateUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author EvanLuo
  */
@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class User extends BaseData {
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 个人说明
     */
    private String description;
    /**
     * email
     */
    private String email;
    /**
     * 金钱
     */
    private Integer money;
    /**
     * 食物
     */
    private Integer food;
    /**
     * 策略
     */
    @Enumerated(EnumType.STRING)
    private TacticEnum tactic;
    /**
     * 水手
     */
    private Integer sailor;
    /**
     * 战斗力
     */
    private Integer attack;
    /**
     * 指挥力
     */
    private Integer command;
    /**
     * 航海力
     */
    private Integer navigation;
    /**
     * 贸易名声
     */
    private Integer trade;
    /**
     * 冒险名声
     */
    private Integer adventure;
    /**
     * 战斗名声
     */
    private Integer battle;
    /**
     * 贸易经验
     */
    private Integer tradeExp;
    /**
     * 冒险经验
     */
    private Integer adventureExp;
    /**
     * 战斗经验
     */
    private Integer battleExp;
    /**
     * 最后访问时间
     */
    private LocalDateTime lastAccessTime;
    /**
     * 移动到达时间
     */
    private LocalDateTime moveTime;
    /**
     * 原项目中的action标记,防作弊
     */
    private String action;
    /**
     * 最近一次战斗的时间
     */
    private LocalDateTime battleTime;
    /**
     * 所在港内位置
     */
    @Enumerated(EnumType.STRING)
    private PointEnum point;
    /**
     * 所在海域
     */
    @JoinColumn(name = "area_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Area area;
    /**
     * 所在港口
     */
    @JoinColumn(name = "port_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Port port;
    /**
     * 持有的商品
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserGoods> userGoodsList;
    /**
     * 持有的宝物
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserItem> userItemList;
    /**
     * 持有的船只
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserShip> userShipList;
    /**
     * 进行或已完成任务
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserAdventure> userAdventureList;
    /**
     * 持有的城镇
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserPortCity> userPortCityList;
    /**
     * 操作记录
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserOperation> userOperationList;
    /**
     * 持有的存款
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserPortCityDeposit> userPortCityDepositList;
    /**
     * 用户关系
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST}, mappedBy = "user", orphanRemoval = true)
    private List<UserFriendship> userFriendshipList;

    /**
     * 回避率
     */
    public int getAvoid() {
        Integer command = getCommand();
        if (command == null) {
            return 0;
        }
        return CalculateUtils.avoid(command);
    }

    /**
     * 冒险等级
     */
    public int getAdventureLevel() {
        Integer adventureExp = getAdventureExp();
        if (adventureExp == null) {
            return 0;
        }
        return CalculateUtils.level(adventureExp, getUserItemList().size());
    }

    /**
     * 战斗等级
     */
    public int getBattleLevel() {
        Integer piracyExp = getBattleExp();
        if (piracyExp == null) {
            return 0;
        }
        return CalculateUtils.level(piracyExp, getUserItemList().size());
    }

    /**
     * 商人等级
     */
    public int getTradeLevel() {
        Integer tradeExp = getTradeExp();
        if (tradeExp == null) {
            return 0;
        }
        return CalculateUtils.level(tradeExp, getUserItemList().size());
    }

    /**
     * 航速
     */
    public int getVector() {
        List<UserShip> userShipList = getUserShipList();
        if (userShipList.isEmpty()) {
            return 0;
        }
        double totalSpeed = userShipList.stream()
                .map(UserShip::getShip)
                .map(Ship::getSpeed)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        return CalculateUtils.vector(totalSpeed, userShipList.size(), getNavigation(), getAdventureLevel());
    }

    /**
     * 总容积
     */
    public int getTotalVolume() {
        return getUserShipList()
                .stream()
                .mapToInt(UserShip::getVolume)
                .sum();
    }

    /**
     * 总货物数量
     */
    public int getTotalGoodsQuantity() {
        return getUserGoodsList()
                .stream()
                .mapToInt(UserGoods::getQuantity)
                .sum();
    }

    /**
     * 剩余容积
     */
    public int getRemainVolume() {
        return CalculateUtils.remainVolume(getTotalVolume(), getTotalGoodsQuantity(), getFood(), getSailor());
    }

    /**
     * 移动所需时间
     */
    public long getMovingSecond() {
        return LocalDateTime.now().until(getMoveTime(), ChronoUnit.SECONDS);
    }

    /**
     * 移动中
     */
    public boolean isMoving() {
        return getMovingSecond() > 0;
    }

    public void addUserShip(UserShip userShip) {
        userShip.setUser(this);
        getUserShipList().add(userShip);
    }

    public void removeUserShip(UserShip userShip) {
        int totalVolume = getTotalVolume();
        //物资保留率
        double saveRate = totalVolume == 0 ?
                0 : 1 - ((double) userShip.getVolume() / (double) totalVolume);
        setFood((int) (getFood() * saveRate));
        getUserGoodsList()
                .forEach(userGoods -> userGoods.setQuantity((int) (userGoods.getQuantity() * saveRate)));
        getUserShipList().remove(userShip);
    }
}
