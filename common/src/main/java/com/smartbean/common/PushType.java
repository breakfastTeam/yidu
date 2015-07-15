package com.smartbean.common;

/**
 * Created by Administrator on 2015/6/28.
 */
public enum PushType {
    ARTICLE("article");
    String value;

    private PushType(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}
