package com.smartbean.repository;

import com.smartbean.entity.Article;
import com.smartbean.entity.WechatType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface WechatTypeRepository extends JpaRepository<WechatType, String>, JpaSpecificationExecutor<WechatType> {
    List<WechatType> findByParentIdIsNull();

    List<WechatType> findByParentId(String paretId);
}
