package com.smartbean.service.impl;

import com.smartbean.entity.Subject;
import com.smartbean.entity.Wechat;
import com.smartbean.entity.WechatType;
import com.smartbean.repository.WechatRepository;
import com.smartbean.repository.WechatTypeRepository;
import com.smartbean.service.WechatService;
import com.smartbean.service.WechatTypeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Created by Administrator on 2015/6/25.
 */
@Service("wechatTypeService")
@Transactional
public class WechatTypeServiceImpl implements WechatTypeService {

    @Autowired
    private WechatTypeRepository wechatTypeRepository;

    @Override
    public WechatType saveOrUpdate(WechatType wechatType) {
        return wechatTypeRepository.save(wechatType);
    }

    @Override
    public List<WechatType> save(List<WechatType> wechatTypes){
        return wechatTypeRepository.save(wechatTypes);
    }

    @Override
    public Page<WechatType> findAll(Pageable page, final WechatType wechatType) {
        return wechatTypeRepository.findAll(new Specification<WechatType>() {
            @Override
            public Predicate toPredicate(Root<WechatType> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (StringUtils.isNotBlank(wechatType.getName())) {
                    expressions.add(cb.equal(root.get("name").as(String.class), wechatType.getName()));
                }
                return predicate;
            }
        }, page);
    }

    @Override
    public WechatType findOne(String id) {
        return wechatTypeRepository.findOne(id);
    }

    @Override
    public List<WechatType> findAll() {
        return wechatTypeRepository.findAll();
    }

    @Override
    public List<WechatType> findAllFirstLevel() {
        return wechatTypeRepository.findByParentIdIsNull();
    }

    @Override
    public List<WechatType> findSecondLevel(String parentId) {
        return wechatTypeRepository.findByParentId(parentId);
    }
}
