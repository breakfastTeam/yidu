package com.smartbean.service;

import com.smartbean.entity.Customer;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface CustomerService {

    /**
     * 保存客户表
     * **/
    public Customer save(Customer customer);

    /**
     * 根据OpenId获取客户信息
     * **/
    public Customer findByOpenId(String openId);

    /**
     * 获取所有符合条件的客户信息
     * **/
    public List<Customer> findAll(Customer customer);

    /**
     * 获取微信公众账号的所有粉丝情况
     * **/
    public List<Customer> findAllFans();

}
