package com.smartbean.mobile.model;

import java.util.List;

/**
 * Created by Administrator on 2015/7/5.
 */
public class WechatPageModel {
    private List<WechatModel> wechatModel;
    private boolean isLast;

    public List<WechatModel> getWechatModel() {
        return wechatModel;
    }

    public void setWechatModel(List<WechatModel> wechatModel) {
        this.wechatModel = wechatModel;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }
}
