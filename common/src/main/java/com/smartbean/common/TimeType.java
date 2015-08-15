package com.smartbean.common;

/**
 * Created by Administrator on 2015/6/28.
 */
public enum TimeType {
    YEAR("year"),
    MOUTH("mouth"),
    DAY("day"),
    HOUR("hour"),
    MINUTE("minute"),
    OTHER("other");
    String value;

    private TimeType(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}
