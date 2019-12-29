package com.luoyifan.voyage.constants;

/**
 * @author EvanLuo
 */
public enum BrowserEnum {
    IE("IE"),
    CHROME("Chrome"),
    FIRE_FOX("FireFox"),
    SAFARI("Safari"),
    QQ("QQ"),
    UC("UC"),
    QH360("360"),
    MAXTHON("傲游"),
    UNKNOWN("未知");

    private String description;

    BrowserEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
