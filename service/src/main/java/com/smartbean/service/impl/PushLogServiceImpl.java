package com.smartbean.service.impl;


import com.google.common.collect.Lists;
import com.smartbean.entity.PushLog;
import com.smartbean.entity.ReadLog;
import com.smartbean.repository.PushLogRepository;
import com.smartbean.repository.ReadLogRepository;
import com.smartbean.service.PushLogService;
import com.smartbean.service.ReadLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
@Service("pushLogService")
@Transactional
public class PushLogServiceImpl implements PushLogService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private PushLogRepository pushLogRepository;

    @Override
    public PushLog save(PushLog pushLog) {
        return pushLogRepository.save(pushLog);
    }

    @Override
    public List<PushLog> save(List<PushLog> pushLogs) {
        return pushLogRepository.save(pushLogs);
    }

    @Override
    public boolean isPushed(String customerId, String typeId) {
        List<PushLog> readLogs = pushLogRepository.findByCustomerIdAndTypeId(customerId, typeId);
        if(readLogs != null && readLogs.size()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<String> getPushedArticlesId(String customerId) {
        List<String> ids = Lists.newArrayList();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<PushLog> pushLogRoot = cq.from(PushLog.class);
        cq.select(cb.tuple(pushLogRoot.get(pushLogRoot.getModel().getSingularAttribute("typeId", String.class))));
        cq.where(
                cb.equal(pushLogRoot.get(pushLogRoot.getModel().getSingularAttribute("customerId", String.class)), customerId)
        );

        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();

        for (Tuple tuple : result) {
            ids.add((String) tuple.get(0));
        }
        return ids;
    }
}
