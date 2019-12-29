package com.luoyifan.voyage.constants;

/**
 * 酒馆操作
 * @author EvanLuo
 */
public enum BarOperationEnum {
    EMPLOY("雇佣水手"),
    FIRE("解雇水手"),
    PURCHASE_FOOD("购买食物"),
    THROW_FOOD("丢弃食物"),
    ADVENTURE("听取情报")
    ;
    private String description;

    BarOperationEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
