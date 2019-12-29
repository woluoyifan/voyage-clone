package com.luoyifan.voyage.constants;

/**
 * @author EvanLuo
 */
public enum PlatformEnum {
    WINDOWS("Windows"),
    MAC("MacOS"),
    ANDROID("Android"),
    LINUX("Linux"),
    UNKNOWN("未知");

    private String description;

    PlatformEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
