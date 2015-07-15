package com.smartbean.mobile.model;

/**
 * Created by Administrator on 2015/6/28.
 */
public class WechatModel {
    private String id;
    private long subCount;
    private String name;
    private String openId;
    private String logo;
    private String account;
    private String company;
    private String intro;
    private boolean isSub;
    private boolean isLast;

    public long getSubCount() {
        return subCount;
    }

    public void setSubCount(long subCount) {
        this.subCount = subCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean isSub) {
        this.isSub = isSub;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean isLast) {
        this.isLast = isLast;
    }
}
