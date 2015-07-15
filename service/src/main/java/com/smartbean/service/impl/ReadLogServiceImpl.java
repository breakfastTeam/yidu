package com.smartbean.service.impl;


import com.google.common.collect.Lists;
import com.smartbean.entity.ReadLog;
import com.smartbean.entity.Subject;
import com.smartbean.repository.ReadLogRepository;
import com.smartbean.repository.SubjectRepository;
import com.smartbean.service.ReadLogService;
import com.smartbean.service.SubjectService;
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
 * Created by Administrator on 2015/6/24.
 */
@Service("readLogService")
@Transactional
public class ReadLogServiceImpl implements ReadLogService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private ReadLogRepository readLogRepository;


    @Override
    public ReadLog save(ReadLog readLog) {
        return readLogRepository.save(readLog);
    }

    @Override
    public boolean isRead(String customerId, String articleId) {
        List<ReadLog> readLogs = readLogRepository.findByCustomerIdAndArticleId(customerId, articleId);
        if(readLogs != null && readLogs.size()>0){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public List<String> getReadArticlesId(String customerId) {
        List<String> ids = Lists.newArrayList();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<ReadLog> readLogRoot = cq.from(ReadLog.class);
        cq.select(cb.tuple(readLogRoot.get(readLogRoot.getModel().getSingularAttribute("articleId", String.class))));
        cq.where(
                cb.equal(readLogRoot.get(readLogRoot.getModel().getSingularAttribute("customerId", String.class)), customerId)
        );

        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();

        for (Tuple tuple : result) {
            ids.add((String) tuple.get(0));
        }
        return ids;
    }
}
