package com.smartbean.service;

import com.smartbean.entity.Customer;
import com.smartbean.entity.Wechat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface WechatService {
    /**
     * 保存微信号
     * **/
    public Wechat save(Wechat wechat);


    /**
     * 根据微信号删除公众号信息
     * **/
    public void delete(String wechatId);

    /**
     * 抓取微信公众账号的详细信息
     * **/
    public Wechat grabWechatByOpenId(String openId);

    /**
     * 批量保存微信号
     * **/
    public List<Wechat> save(List<Wechat> wechats);

    /**
     * 根据账户查找微信号信息
     * **/
    public Wechat findByAccount(String account);

    /**
     * 根据微信openId查询微信公众账号信息
     * **/
    public Wechat findByOpenId(String openId);

    /**
     * 查询所有符合条件的微信公众号，并分页展示
     * **/
    public Page<Wechat> findAll(Pageable page, Wechat wechat);

    /**
     * 获取所有的微信公众账号
     * **/
    public List<Wechat> findAll();

    /**
     * 获取某一个类型下的所有微信公众账号并分页展示出来
     * **/
    public Page<Wechat> findByType(Pageable page, String typeId);


    /**
     * 根据微信公众号主键查找微信公众账号
     * **/
    public Wechat findOne(String id);

    /**
     * 根据用户主键查询所有主键信息
     * **/
    public List<Wechat> findByCustomerId(String customerId);


    /**
     * 更改微信公众账号类型
     * **/
    public Wechat changeWechatType(String wechatId, String wechatTypeId);

    /**
     * 根据关键字查询网络上的微信公众账号
     * **/
    public List<Wechat> searchWechatFromInternet(String keyword);

}
