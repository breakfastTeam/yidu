package com.smartbean.service.impl;

import com.google.common.collect.Lists;
import com.smartbean.common.ArticleStatus;
import com.smartbean.entity.Article;
import com.smartbean.entity.Subject;
import com.smartbean.entity.Subscribe;
import com.smartbean.entity.Wechat;
import com.smartbean.repository.ArticleRepository;
import com.smartbean.repository.SubscribeRepository;
import com.smartbean.repository.WechatRepository;
import com.smartbean.service.ArticleService;
import com.smartbean.service.SubjectService;
import com.smartbean.service.SubscribeService;
import com.smartbean.service.WechatService;
import com.smartbean.util.internet.Spider;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;
/**
 * Created by Administrator on 2015/6/28.
 */
@Service("subscribeService")
@Transactional
public class SubscribeServiceImpl implements SubscribeService{

    @Autowired
    private SubscribeRepository subscribeRepository;

    @Autowired
    private WechatRepository wechatRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private WechatService wechatService;

    @Autowired
    private ArticleService articleService;

    @PersistenceContext
    private EntityManager em;

    @Override
    public Subscribe subscribe(String wechatId, String customerId) {
        Subscribe subscribe = this.getSubscribeByWechatIdAndCustomerId(wechatId, customerId);

        if(subscribe == null){
            subscribe = new Subscribe();
            subscribe.setCustomerId(customerId);
            subscribe.setWechatId(wechatId);
            subscribeRepository.save(subscribe);

            Wechat wechat = wechatRepository.findOne(wechatId);
            wechat.setNum(wechat.getNum()+1);
            wechatRepository.saveAndFlush(wechat);
        }
        return subscribe;
    }

    @Override
    public Subscribe subscribeByOpenId(String openId, String customerId) {
        Wechat wechat = wechatService.grabWechatByOpenId(openId);
        wechatRepository.save(wechat);
        List<Article> articles = articleService.grabWechatLastArticle(openId, wechat.getId());
        articleRepository.save(articles);
        return this.subscribe(wechat.getId(), customerId);
    }

    @Override
    public void unsubscribe(String wechatId, String customerId) {
        Subscribe subscribe = this.getSubscribeByWechatIdAndCustomerId(wechatId, customerId);
        subscribeRepository.delete(subscribe);

        Wechat wechat = wechatRepository.findOne(wechatId);
        wechat.setNum(wechat.getNum()-1);
        wechatRepository.saveAndFlush(wechat);
    }

    private Subscribe getSubscribeByWechatIdAndCustomerId(String wechatId, String customerId){
        Subscribe subscribe = new Subscribe();
        subscribe.setWechatId(wechatId);
        subscribe.setCustomerId(customerId);
        List<Subscribe> subscribes = this.findAll(subscribe);
        if(subscribes!= null && subscribes.size()>0){
            return subscribes.get(0);
        }else{
            return null;
        }
    }

    @Override
    public List<Subscribe> findAll(final Subscribe subscribe) {
        return subscribeRepository.findAll(new Specification<Subscribe>() {
            @Override
            public Predicate toPredicate(Root<Subscribe> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringUtils.isNotBlank(subscribe.getWechatId())) {
                    expressions.add(cb.equal(root.get("wechatId").as(String.class), subscribe.getWechatId()));
                }
                if (StringUtils.isNotBlank(subscribe.getCustomerId())) {
                    expressions.add(cb.equal(root.get("customerId").as(String.class), subscribe.getCustomerId()));
                }

                return predicate;
            }
        });
    }

    @Override
    public long getSubCount(String wechatId) {
        return subscribeRepository.countByWechatId(wechatId);
    }

    @Override
    public List<Subscribe> findByCustomerId(String customerId) {
        Subscribe subscribe = new Subscribe();
        subscribe.setCustomerId(customerId);
        return this.findAll(subscribe);
    }

    @Override
    public List<String> findWechatIdsByCustomerId(String customerId) {
        List<String> ids = Lists.newArrayList();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Subscribe> subscribeRoot = cq.from(Subscribe.class);
        cq.select(cb.tuple(subscribeRoot.get(subscribeRoot.getModel().getSingularAttribute("wechatId", String.class))));
        cq.where(
                cb.equal(subscribeRoot.get(subscribeRoot.getModel().getSingularAttribute("customerId", String.class)), customerId)
        );

        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();

        for (Tuple tuple : result) {
            ids.add((String) tuple.get(0));
        }
        return ids;
    }

    @Override
    public List<String> getSubscribWechatId(String customerId) {
        List<String> ids = Lists.newArrayList();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Subscribe> subscribeRoot = cq.from(Subscribe.class);
        cq.select(cb.tuple(subscribeRoot.get(subscribeRoot.getModel().getSingularAttribute("wechatId", String.class))));
        cq.where(
                cb.equal(subscribeRoot.get(subscribeRoot.getModel().getSingularAttribute("customerId", String.class)), customerId)
        );

        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();

        for (Tuple tuple : result) {
            ids.add((String) tuple.get(0));
        }
        return ids;
    }
}
