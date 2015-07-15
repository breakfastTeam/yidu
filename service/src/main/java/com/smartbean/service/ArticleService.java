package com.smartbean.service;


import com.smartbean.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
public interface ArticleService {

    /**
     * 根据主键获取文章详情
     * **/
    public Article findOne(String id);

    /**
     * 保存微信文章
     * **/
    public Article save(Article article);

    /**
     * 批量保存微信文章
     * **/
    public List<Article> save(List<Article> articles);


    /**
     * 保存或者更新文章信息
     * **/
    public Article saveOrUpdate(Article article);

    /**
     * 根据主题获取文章列表
     * **/
    public Page<Article> getBySubjectName(Pageable page, String subjectName);

    /**
     * 根据主题获取文章列表
     * **/
    public Page<Article> getBySubjectId(Pageable page, String subjectId);


    /**
     * 根据公众号获取文章
     * **/
    public Page<Article> getByWechat(Pageable page, String wechatId);

    /**
     * 获取所有符合条件的文章
     * **/
    public Page<Article> findAll(Pageable page, Article article);

    /**
     * 根据详情地址获取文章
     * **/
    public Article getByDetailUrl(String url);

    /**
     * 抓取搜狗热门文章
     * **/
    public void grabHotArticleFromSogou();

    /**
     * 抓取公众号文章
     * **/
    public void grabWechatArticle();

    /**
     * 抓取某一个微信号下的最新文章
     * **/
    public List<Article> grabWechatLastArticle(String openId, String wechatId);


    /**
     * 获取我关注的文章列表
     * **/

    public Page<Article> getByCustomer(Pageable page, String customerId);

    /**
     * 发布文章
     * **/
    public Article publish(String articleId);

    /**
     * 下架文章列表
     * **/
    public Article unpublish(String articleId);

    /**
     * 更改文章分类
     * **/
    public Article changeSubject(String articleId, String subjectId);

    /**
     * 获取我的未读文章
     * **/
    public Page<Article> getUnreadArticles(Pageable page, String customerId);

    /**
     * 获取我订阅的公众号的未读文章
     * **/
    public Page<Article> getUnreadWechatArticles(Pageable page, String customerId);

    /**
     * 获取未推送的热门文章
     * **/
    public Page<Article> getUnpushedArticles(Pageable page, String customerId);

    /**
     * 获取未推送并未阅读的热门文章
     * **/
    public Page<Article> getUnpushedAndUnreadArticles(Pageable page, String customerId);

    /**
     * 获取我订阅的公众号的未推送的文章
     * **/
    public Page<Article> getUnpushedWechatArticles(Pageable page, String customerId);

    /**
     * 获取我订阅的公众号的未推送的文章
     * **/
    public Page<Article> getUnpushedAndUnreadWechatArticles(Pageable page, String customerId);



    /**
     * 是否有未读文章
     * **/
    public boolean hasUnreadArticles(String customerId);

    /**
     * 我的订阅下是否有未读文章
     * **/
    public boolean hasUnreadWechatArticles(String customerId);


    /**
     * 推送最新文章
     * **/
    public void pushNewHotArticles();

    /**
     * 推送微信公众号文章
     * **/
    public void pushWechatArticles();

 }
