package com.luoyifan.voyage.constants;

/**
 * @author EvanLuo
 */
public enum UserSortEnum {
    NAME("名字"),
    MONEY("资金"),
    ADVENTURE("冒险"),
    BATTLE("战斗"),
    TRADE("商人"),
    SHIP("船只"),
    AREA("海域"),
    ;
    private String description;

    UserSortEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
