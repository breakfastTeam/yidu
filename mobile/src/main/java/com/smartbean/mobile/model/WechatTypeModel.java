package com.smartbean.mobile.model;

import com.smartbean.entity.Article;
import com.smartbean.entity.WechatType;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
public class WechatTypeModel {
    private WechatType firstLevel;
    private List<WechatType> secondLevel;

    public WechatType getFirstLevel() {
        return firstLevel;
    }

    public void setFirstLevel(WechatType firstLevel) {
        this.firstLevel = firstLevel;
    }

    public List<WechatType> getSecondLevel() {
        return secondLevel;
    }

    public void setSecondLevel(List<WechatType> secondLevel) {
        this.secondLevel = secondLevel;
    }
}
