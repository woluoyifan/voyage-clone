package com.luoyifan.voyage.constants;

/**
 * @author EvanLuo
 */
public enum UserOperationEnum {
    LOGIN("登入"),
    LOGOUT("登出"),
    MOVE("移动"),
    GOODS("货物"),
    SHIP("舰船"),
    SAILOR("水手"),
    FOOD("食物"),
    CITY("城镇"),
    BATTLE("战斗"),
    ADVENTURE("冒险"),
    MAIL("消息"),
    ;

    UserOperationEnum(String description) {
        this.description = description;
    }

    private String description;

    public String getDescription() {
        return description;
    }
}
