package com.smartbean.service.impl;

import com.google.common.collect.Lists;
import com.smartbean.common.WechatStatus;
import com.smartbean.entity.Customer;
import com.smartbean.entity.Subscribe;
import com.smartbean.entity.Wechat;
import com.smartbean.repository.WechatRepository;
import com.smartbean.service.SubscribeService;
import com.smartbean.service.WechatService;
import com.smartbean.util.internet.Spider;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
 * Created by Administrator on 2015/6/25.
 */
@Service("wechatService")
@Transactional
public class WechatServiceImpl implements WechatService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private WechatRepository wechatRepository;

    @Autowired
    private SubscribeService subscribeService;

    @Override
    public Wechat save(Wechat wechat) {
        return wechatRepository.save(wechat);
    }

    @Override
    public void delete(String wechatId) {
        wechatRepository.delete(wechatId);
    }

    @Override
    public Wechat grabWechatByOpenId(String openId) {
        Spider spider = new Spider();
        String[] wechatSogou = spider.getWechatFromSogou(openId);
        Wechat wechat = new Wechat();
        wechat.setLogo(wechatSogou[0]);
        wechat.setName(wechatSogou[1]);
        wechat.setAccount(wechatSogou[2]);
        wechat.setBriefIntro(wechatSogou[3]);
        wechat.setCompany(wechatSogou[4]);
        wechat.setCreateTime(DateTime.now());
        wechat.setNum(1);
        return wechat;
    }

    @Override
    public List<Wechat> save(List<Wechat> wechats){
        return wechatRepository.save(wechats);
    }

    @Override
    public Wechat findByAccount(String account) {
        return wechatRepository.findByAccount(account);
    }

    @Override
    public Wechat findByOpenId(String openId) {
        return wechatRepository.findByOpenId(openId);
    }

    @Override
    public Page<Wechat> findAll(Pageable page, final Wechat wechat) {
        return wechatRepository.findAll(new Specification<Wechat>() {
            @Override
            public Predicate toPredicate(Root<Wechat> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringUtils.isNotBlank(wechat.getAccount())) {
                    expressions.add(cb.equal(root.get("account").as(String.class), wechat.getAccount()));
                }
                if (StringUtils.isNotBlank(wechat.getOpenId())) {
                    expressions.add(cb.equal(root.get("openId").as(String.class), wechat.getOpenId()));
                }
                if (StringUtils.isNotBlank(wechat.getTypeId())) {
                    expressions.add(cb.equal(root.get("typeId").as(String.class), wechat.getTypeId()));
                }
                if (StringUtils.isNotBlank(wechat.getName())) {
                    expressions.add(cb.like(root.get("name").as(String.class), "%"+wechat.getName()+"%"));
                }


                return predicate;
            }
        }, page);
    }

    @Override
    public List<Wechat> findAll() {
        return wechatRepository.findAll();
    }

    @Override
    public Page<Wechat> findByType(Pageable page, String typeId) {
        Wechat wechat = new Wechat();
        wechat.setTypeId(typeId);
        return this.findAll(page, wechat);
    }

    @Override
    public Wechat findOne(String id) {
        return wechatRepository.findOne(id);
    }

    @Override
    public List<Wechat> findByCustomerId(String customerId) {
        List<String> wechatIds = subscribeService.findWechatIdsByCustomerId(customerId);
        return wechatRepository.findByIdIn(wechatIds);

    }

    @Override
    public Wechat changeWechatType(String wechatId, String wechatTypeId) {
        Wechat wechat = wechatRepository.findOne(wechatId);
        wechat.setTypeId(wechatTypeId);
        wechatRepository.save(wechat);
        return wechat;
    }

    @Override
    public List<Wechat> searchWechatFromInternet(String keyword) {
        Spider spider = new Spider();
        List<String[]> wechats = spider.searchWechatFromSogou(keyword);
        List<Wechat> wechatList = Lists.newArrayList();
        for(String[] w : wechats){
            Wechat wechat = this.findByOpenId(w[1]);
            if(wechat == null) {
                wechat = new Wechat();
                wechat.setLogo(w[0]);
                wechat.setOpenId(w[1]);
                wechat.setName(w[2]);
                wechat.setAccount(w[3]);
                wechat.setBriefIntro(w[4]);
                wechat.setCompany(w[5]);
                wechat.setCreateTime(DateTime.now());
                wechatList.add(wechat);
            }
        }
        return wechatList;
    }



}
