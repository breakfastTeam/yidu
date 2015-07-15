package com.smartbean.common;

/**
 * Created by qingfeilee on 2015/4/18.
 */
public class PageConfig {
    public static final int PAGE_SIZE = 15;

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private int pageNo;
}
