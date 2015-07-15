package com.smartbean.common;

/**
 * Created by Administrator on 2015/6/28.
 */
public enum SessionData {
    CUSTOMER_ID("customerId"),
    OPEN_ID("open_id");
    String value;

    private SessionData(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}
