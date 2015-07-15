package com.smartbean.service;


import com.smartbean.entity.Subject;
import com.smartbean.entity.WechatType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface WechatTypeService {

    /**
     * 保存微信类型
     * **/
    public WechatType saveOrUpdate(WechatType wechatType);

    /**
     * 保存微信类型
     * **/
    public List<WechatType> save(List<WechatType> wechatTypes);



    /**
     * 获取所有符合条件的微信类型
     * **/
    public Page<WechatType> findAll(Pageable page, WechatType wechatType);

    /**
     * 获取某一个分类
     * **/
    public WechatType findOne(String id);

    /**
     * 获取所有微信分类
     * **/
    public List<WechatType> findAll();

    /**
     * 获取所有一级分类
     * **/

    public List<WechatType> findAllFirstLevel();

    /**
     * 获取所有二级分类
     * **/

    public List<WechatType> findSecondLevel(String parentId);

}
