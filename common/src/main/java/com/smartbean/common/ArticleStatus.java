package com.smartbean.common;

/**
 * Created by Administrator on 2015/6/28.
 */
public enum ArticleStatus {
    UNPUBLISHED("unpublished"),
    PUBLISHED("published"),
    DELETE("delete");
    String value;

    private ArticleStatus(String value) {
        this.value = value;
    }
    public String toString() {
        return this.value;
    }
}
