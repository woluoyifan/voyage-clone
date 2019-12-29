package com.luoyifan.voyage.constants;

/**
 * 位置
 * @author EvanLuo
 */
public enum PointEnum {
    PORT("港口"),
    TRADE("交易"),
    YARD("造船"),
    BAR("酒馆"),
    CITY("城镇"),
    BATTLE("袭击"),
    ;

    private String description;

    PointEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
