package com.luoyifan.voyage.constants;

/**
 * @author EvanLuo
 */
public enum ShipTypeEnum {
    SHIP("船只"),
    ATTACK("战斗力"),
    COMMAND("指挥力"),
    NAVIGATION("航海力");

    private String description;

    ShipTypeEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
