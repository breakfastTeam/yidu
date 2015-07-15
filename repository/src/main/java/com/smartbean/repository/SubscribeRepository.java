package com.smartbean.repository;

import com.smartbean.entity.Article;
import com.smartbean.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface SubscribeRepository extends JpaRepository<Subscribe, String>, JpaSpecificationExecutor<Subscribe> {
    public long countByWechatId(String wechatId);
}
