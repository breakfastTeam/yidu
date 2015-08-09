package com.smartbean.model;

import java.util.List;

/**
 * Created by Administrator on 2015/8/2.
 */
public class WechatArticleModel {
    private BriefWechatModel briefWechatModel;
    private List<BriefArticleModel> briefArticleModels;

    public BriefWechatModel getBriefWechatModel() {
        return briefWechatModel;
    }

    public void setBriefWechatModel(BriefWechatModel briefWechatModel) {
        this.briefWechatModel = briefWechatModel;
    }

    public List<BriefArticleModel> getBriefArticleModels() {
        return briefArticleModels;
    }

    public void setBriefArticleModels(List<BriefArticleModel> briefArticleModels) {
        this.briefArticleModels = briefArticleModels;
    }
}
