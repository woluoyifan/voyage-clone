package com.luoyifan.voyage.constants;

/**
 * 造船所操作
 * @author EvanLuo
 */
public enum YardOperationEnum {
    PURCHASE("购买"),
    SELL("售出"),
    REPAIR("修理")
    ;

    private String description;

    YardOperationEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
