package com.luoyifan.voyage.constants;

/**
 * 战斗策略
 * @author EvanLuo
 */
public enum TacticEnum {
    WARLIKE("好战"),
    COMMON("适度"),
    AVOID("回避"),
    SURRENDER("投降（名声归0）"),
    MOORING("靠港"),
    ;

    private String description;

    TacticEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
