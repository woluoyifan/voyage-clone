package com.luoyifan.voyage.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

/**
 * @author EvanLuo
  */
public class CalculateUtils {
    /**
     * 水手雇佣价格
     *
     * @param employPrice 水手雇佣基础价
     * @param piracyLevel 海贼等级
     */
    public static int employPrice(int employPrice, int piracyLevel) {
        return employPrice * (100 - piracyLevel) / 100;
    }

    /**
     * 商品购买价格
     *
     * @param goodsName          商品名
     * @param price              价格
     * @param priceIndex         价格系数
     * @param priceChangeModules 价格变更系数
     * @param tr                 日期变更系数
     */
    public static int purchasePrice(String goodsName, int price, double priceIndex, int priceChangeModules, int tr) {
        return price(goodsName, price, priceIndex, priceChangeModules, tr);
    }

    /**
     * 商品出售价格
     *
     * @param goodsName          商品名
     * @param price              价格
     * @param priceIndex         价格指数
     * @param priceChangeModules 价格变更系数
     * @param tr                 日期变更系数
     * @param sellRate           出售折价率
     * @return
     */
    public static int sellPrice(String goodsName, int price, double priceIndex, int priceChangeModules, int tr, double sellRate) {
        return (int) (purchasePrice(goodsName, price, priceIndex, priceChangeModules, tr) * sellRate);
    }

    /**
     * 所需食物
     *
     * @param second 旅程时间
     * @param sailor 水手数量
     * @param waste  每水手食物消耗数
     */
    public static int food(int second, int sailor, double waste) {
        return (int) (second * waste * (sailor + 100));
    }

    /**
     * 平复物价系数波动
     *
     * @param priceIndex 原始物价系数
     */
    public static double convergePriceIndex(double priceIndex) {
        if (priceIndex > 0.9 && priceIndex < 1.1) {
            return priceIndex;
        }
        return (priceIndex * priceIndex * priceIndex / 6) - (priceIndex * priceIndex / 2) + 1.457 * priceIndex - 0.124;
    }

    /**
     * 计算移动所需时间，秒
     *
     * @param xa        起始x
     * @param ya        起始y
     * @param xb        目标x
     * @param yb        目标y
     * @param speed     速度
     * @param timeScale 时间尺度
     * @return
     */
    public static int moveTime(int xa, int ya, int xb, int yb, int speed, int timeScale) {
        if (speed <= 0) {
            return 0;
        }
        double baseSec = distance(xa, ya, xb, yb);
        return (int) (baseSec * timeScale / speed);
    }

    /**
     * 距离计算
     *
     * @param xa 起始x
     * @param ya 起始y
     * @param xb 目标x
     * @param yb 目标y
     */
    public static double distance(int xa, int ya, int xb, int yb) {
        int y = Math.abs(ya - yb);
        if (y > 180) {
            y = 360 - y;
        }
        return Math.sqrt((xa - xb) * (xa - xb) + y * y);
    }


    /**
     * 计算物价变量 tr
     *
     * @param date     时间
     * @param cycle    物价变动周期
     * @param areaCode 区域code,大区,如伊比利亚1001
     * @return
     */
    public static int tr(LocalDate date, int cycle, int areaCode) {
        return (date.getDayOfMonth() / cycle + date.getMonthValue()) * (areaCode % 1000);
    }

    /**
     * 计算价格指数变化量
     *
     * @param quantity           商品数量
     * @param price              当前价格
     * @param oneDollarInfluence 一元影响力
     * @return
     */
    public static double priceIndexChange(int quantity, int price, double oneDollarInfluence, double maxPriceIndexChange) {
        int upDown = price * quantity;
        //数量越大，价格越高，值越大
        double pUpDown = oneDollarInfluence * (quantity * 50 + upDown);
        if (pUpDown > maxPriceIndexChange) {
            pUpDown = maxPriceIndexChange;
        }
        return pUpDown;
    }

    /**
     * 计算trade经验或exp
     *
     * @param quantity
     * @param price
     * @param rate
     * @return
     */
    public static int tradeChange(int quantity, int price, int rate) {
        return quantity * price / rate;
    }

    /**
     * 计算价格,地板结果
     *
     * @param goodsName
     * @param basePrice
     * @param priceIndex
     * @param priceChangeModules
     * @param tr
     * @return
     */
    public static int price(String goodsName, int basePrice, double priceIndex, int priceChangeModules, int tr) {
        return (int) (basePrice * priceIndex * fluctuate(goodsName, priceChangeModules, tr));
    }


    /**
     * 计算物价波动
     *
     * @param goodsName          商品名
     * @param priceChangeModules 波动指数
     * @param tr
     * @return 波动数
     */
    public static double fluctuate(String goodsName, int priceChangeModules, int tr) {
        double res = 0;
        int[] unpack = unpack(goodsName);
        for (int u : unpack) {
            res += u * priceChangeModules * tr;
        }
        res = (Math.sin(res) * 0.3) + 1;
        return res;
    }

    /**
     * 模仿perl中unpack(C*, ? )方法
     *
     * @param str
     * @return
     */
    private static int[] unpack(String str) {
        byte[] bytes = str.getBytes();
        int[] res = new int[bytes.length];
        for (int i = 0, len = bytes.length; i < len; i++) {
            res[i] = getUnsignedByte(bytes[i]);
        }
        return res;
    }

    private static int getUnsignedByte(byte data) {      //将data字节型数据转换为0~255 (0xFF 即BYTE)。
        return data & 0x0FF;
    }

    private static int getUnsignedByte(short data) {      //将data字节型数据转换为0~65535 (0xFFFF 即 WORD)。
        return data & 0x0FFFF;
    }

    private static long getUnsignedIntt(int data) {     //将int数据转换为0~4294967295 (0xFFFFFFFF即DWORD)。
        return data & 0x0FFFFFFFFL;
    }

    /**
     * 计算回避率
     *
     * @param command 指挥力
     */
    public static int avoid(int command) {
        return new BigDecimal(command).divide(new BigDecimal(3), RoundingMode.HALF_UP).intValue();
    }

    /**
     * 计算等级
     *
     * @param exp       经验值
     * @param numOfItem 物品数量
     */
    public static int level(int exp, int numOfItem) {
        return (int) (Math.log(exp * (1 + numOfItem * 0.01) + 4792) * 5.9 - 50);
    }

    /**
     * 航速
     *
     * @param totalSpeed     船只速度的总和
     * @param numOfShip      船只数量
     * @param nav            导航力
     * @param adventureLevel 冒险等级
     */
    public static int vector(double totalSpeed, int numOfShip, int nav, int adventureLevel) {
        return (int) (totalSpeed + (nav * 0.1)) / numOfShip * ((100 + adventureLevel) / 10) / 10;
    }

    /**
     * 剩余仓位
     *
     * @param total      总仓位
     * @param numOfGoods 货物数量
     * @param food       食物数量
     * @param sailor     水手数量
     */
    public static int remainVolume(int total, int numOfGoods, int food, int sailor) {
        return total - numOfGoods - food - sailor;
    }

    /**
     * 船舰售价
     *
     * @param price 价格
     */
    public static int sellShipPrice(int price) {
        return price / 2;
    }


    /**
     * 强化费用上升
     *
     * @return
     */
    public static int abilityPriceAdjustment(int origPrice, int attack, int command, int navigation) {
        return (int) (origPrice * Math.exp((attack + command + navigation) * 0.03 - 3));
    }

    /**
     * 修理后容量调整
     */
    public static int volumeAfterShipRepair(int orig) {
        if (orig > 500) {
            return orig - 20;
        } else if (orig > 100) {
            return orig - 10;
        } else {
            return orig;
        }
    }

    /**
     * 舰船结构耐久
     *
     * @param volume 装载量
     */
    public static int shipVolumeToHp(int volume) {
        return (int) (volume * 0.2);
    }

    /**
     * 舰船结构价值
     *
     * @param volume 装载量
     */
    public static int shipVolumeToPrice(int volume) {
        return volume * 10000;
    }

    /**
     * 维修费用
     *
     * @param price     舰船原始价值或结构价值
     * @param currentHp 当前耐久
     * @param maxHp     最大耐久
     */
    public static int repairPrice(int price, int currentHp, int maxHp) {
        return maxHp == 0 ? 0 : (int) (price * 0.03 * maxHp / currentHp);
    }

    /**
     * 冒险经验
     * @param seq 拥有步骤数
     * @return
     */
    public static int adventureExp(int seq) {
        return 5000 * (seq + 1);
    }
}
