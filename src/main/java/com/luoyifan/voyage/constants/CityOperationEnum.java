package com.luoyifan.voyage.constants;

/**
 * 港口城镇操作
 * @author EvanLuo
 */
public enum CityOperationEnum {
    CREATE("建立城镇"),
    TRADE("购买物品"),
    BANK("城镇银行"),
    ATTACK("城镇破坏"),
    ADMIN("城镇管理"),
    TAX_RATE("更改手续费"),
    DESCRIPTION("广告宣传"),
    NAME("重新命名"),
    ;

    private String description;

    CityOperationEnum(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
