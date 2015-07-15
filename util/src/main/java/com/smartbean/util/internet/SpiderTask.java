package com.smartbean.util.internet;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Administrator on 2015/7/1.
 */
public class SpiderTask implements Callable<Object> {
    private String openId;

    public SpiderTask(String openId) {
        this.openId= openId;
    }

    public Object call() throws Exception {
        Spider spider = new Spider();
        return spider.getWechatLastArticlesFromSogou(openId);
    }
}
