package com.luoyifan.voyage.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author EvanLuo
  */
@ConfigurationProperties(prefix = "voyage")
@Data
@Validated
public class VoyageProperty {
    @NotNull
    private String adminPassword;
    @NotNull
    private String appName;
    @NotNull
    private Integer usernameLengthLimit;
    @NotNull
    private Integer personLimit;
    /**
     * 注册时初始金额
     */
    @NotNull
    private Integer userInitMoney;
    /**
     * 注册时最大能力值
     */
    @NotNull
    private Integer userInitCapacity;
    /**
     * 最大拥有船只数
     */
    @NotNull
    private Integer shipNumLimit;
    /**
     * 战斗力上限
     */
    @NotNull
    private Integer attackLimit;
    /**
     * 指挥力上限
     */
    @NotNull
    private Integer commandLimit;
    /**
     * 航海力上限
     */
    @NotNull
    private Integer navigationLimit;
    /**
     * 价格波动系数,0时不波动
     */
    @NotNull
    private Integer goodsPriceChangeModule;
    /**
     * 交易时每1G的物价影响量
     */
    @NotNull
    private Double oneDollarInfluence;
    /**
     * 最小价格指数
     */
    @NotNull
    private Double minPriceIndex;
    /**
     * 最大价格指数
     */
    @NotNull
    private Double maxPriceIndex;
    /**
     * 最大价格指数变化
     */
    @NotNull
    private Double priceIndexChangeLimit;
    /**
     * 物价变动周期
     */
    @NotNull
    private Integer priceChangeCycle;
    /**
     * 移动所需时间的时间尺度,0最小,500最大
     */
    @NotNull
    private Integer timeScale;
    /**
     * 售出价格比率
     */
    @NotNull
    private Double sellRate;
    /**
     * 交易经验折算比率
     */
    @NotNull
    private Integer tradeChangeRate;
    /**
     * 水手每人每秒所消耗食物量
     */
    @NotNull
    private Double sailorWaste;
    /**
     * 节约食物消耗的物品影响的比率
     */
    @NotNull
    private Double saveFoodRate;
    /**
     * 食物价格
     */
    @NotNull
    private Integer foodPrice;
    /**
     * 水手雇佣价格
     */
    @NotNull
    private Integer employPrice;
    /**
     * 建立城市的价格
     */
    @NotNull
    private Integer cityCreatePrice;
    /**
     * 有钱人建立城市的资金阈值
     */
    @NotNull
    private Integer cityCreatePriceRichThreshold;
    /**
     * 有钱人建立城市花费的金钱比率
     */
    @NotNull
    private Double cityCreatePriceRichRate;
    /**
     * 城镇初始HP
     */
    @NotNull
    private Integer cityInitHp;
    /**
     * 城镇修理的费用(每HP)
     */
    @NotNull
    private Integer cityRepairCost;
    /**
     * 城镇破坏费用
     */
    @NotNull
    private Integer cityMoneyAttackCost;
    /**
     * 城镇破坏HP值
     */
    @NotNull
    private Integer cityMoneyAttackDamage;
    /**
     * 城镇的攻击力
     */
    @NotNull
    private Integer cityAttackPower;
    /**
     * 城镇战斗时的随机变化
     */
    @NotNull
    private Integer cityAttackRandom;
    /**
     * 城镇物品存放数量限制
     */
    @NotNull
    private Integer cityGoodsLimit;
    /**
     * 城镇船只存放数量限制
     */
    @NotNull
    private Integer cityShipLimit;
    /**
     * 用户操作记录显示限制
     */
    @NotNull
    private Integer userOperationDisplayLimit;
    /**
     * 用户列表显示限制
     */
    @NotNull
    private Integer userListDisplayLimit;
    /**
     * 事件记录显示限制
     */
    @NotNull
    private Integer eventRecordDisplayLimit;
    /**
     * 聊天记录显示限制
     */
    @NotNull
    private Integer chatRecordDisplayLimit;
    /**
     * 连续战斗限制（秒）
     */
    @NotNull
    private Integer combatLimit;
    /**
     * 城镇连续战斗限制（秒）
     */
    @NotNull
    private Integer cityCombatLimit;
    /**
     * 相同对手战斗限制（秒）
     */
    @NotNull
    private Integer sameOpponentLimit;
    /**
     * 停泊时间（秒）
     */
    @NotNull
    private Integer mooringTime;
    /**
     * 按注册时间逆序，计数多少以内为新人
     */
    @NotNull
    private Integer rookie;
}
