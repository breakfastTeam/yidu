package com.smartbean.service;

import com.smartbean.entity.Customer;
import com.smartbean.entity.Subscribe;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface SubscribeService {

    /**
     * 订阅
     * **/
    public Subscribe subscribe(String wechatId, String customerId);

    /**
     * 通过微信公众账号的openId来订阅
     * **/
    public Subscribe subscribeByOpenId(String openId, String customerId);

    /**
     * 取消订阅
     * **/
    public void unsubscribe(String wechatId, String customerId);

    /**
     * 获取所有符合条件的订阅
     * **/
    public List<Subscribe> findAll(Subscribe subscribe);

    /**
     * 获取一个微信号的关注量
     * **/
    public long getSubCount(String wechatId);

    /**
     * 获取用户关注的微信号
     * **/

    public List<Subscribe> findByCustomerId(String customerId);

    /**
     * 获取用户关注的微信号主键
     * **/

    public List<String> findWechatIdsByCustomerId(String customerId);

    /**
     * 获取我关注的微信的主键
     * **/
    public List<String> getSubscribWechatId(String customerId);
}
