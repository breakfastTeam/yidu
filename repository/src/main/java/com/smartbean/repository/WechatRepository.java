package com.smartbean.repository;

import com.smartbean.entity.Customer;
import com.smartbean.entity.Wechat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface WechatRepository extends JpaRepository<Wechat, String>, JpaSpecificationExecutor<Wechat> {
    List<Wechat> findByIdIn(List<String> wechatIds);

    Wechat findByOpenId(String openId);

    Wechat findByAccount(String account);
}
