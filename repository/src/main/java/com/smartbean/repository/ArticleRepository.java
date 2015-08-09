package com.smartbean.repository;

import com.smartbean.entity.Article;
import com.smartbean.entity.Wechat;
import org.joda.time.DateTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface ArticleRepository extends JpaRepository<Article, String>, JpaSpecificationExecutor<Article> {
    Page<Article> findByWechatIdInAndStatus(List<String> wechatIds, String status, Pageable page);

    Page<Article> findByWechatIdAndStatus(String wechatId, String status, Pageable page);

    Page<Article> findByIdNotInAndStatusAndCreateTimeGreaterThanEqual(List<String> id, String status,DateTime createTime, Pageable page);

    Page<Article> findByIdNotInAndWechatIdInAndStatusAndCreateTimeGreaterThanEqual(List<String> id, List<String> wechatIds, String status,DateTime createTime, Pageable page);

    Page<Article> findByIdNotInAndIdNotInAndWechatIdInAndStatusAndCreateTimeGreaterThanEqual(List<String> ids, List<String> idz, List<String> wechatIds, String status,DateTime createTime, Pageable page);

    Page<Article> findByIdNotInAndIdNotInAndStatusAndCreateTimeGreaterThanEqual(List<String> ids, List<String> idz,String status, DateTime createTime, Pageable pageable);


    long findCountByIdNotInAndStatus(List<String> id, String status);

    long findCountByIdNotInAndWechatIdInAndStatus(List<String> id, List<String> wechatIds, String status);

    List<Article> findByDetailUrl(String url);
}
