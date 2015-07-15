package com.smartbean.common;

/**
 * Created by Administrator on 2015/6/28.
 */
public enum CustomerStatus {
    FOLLOW("follow"),
    CANCEL("cancel");
    String value;

    private CustomerStatus(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}
