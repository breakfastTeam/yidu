package com.smartbean.common;

/**
 * Created by Administrator on 2015/6/28.
 */
public enum WechatStatus {
    ACTIVE("active"),
    DELETE("delete");
    String value;

    private WechatStatus(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}
