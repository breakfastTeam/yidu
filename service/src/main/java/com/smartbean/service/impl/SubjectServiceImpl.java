package com.smartbean.service.impl;


import com.google.common.collect.Lists;
import com.smartbean.entity.Customer;
import com.smartbean.entity.Subject;
import com.smartbean.repository.SubjectRepository;
import com.smartbean.service.SubjectService;
import com.smartbean.util.internet.Spider;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
@Service("subjectService")
@Transactional
public class SubjectServiceImpl implements SubjectService {

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private SubjectRepository subjectRepository;

    @Override
    public List<Subject> findAll() {
        return subjectRepository.findAll();
    }

    @Override
    public Page<Subject> findAll(Pageable page, final Subject subject) {
        return subjectRepository.findAll(new Specification<Subject>() {
            @Override
            public Predicate toPredicate(Root<Subject> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate predicate = cb.conjunction();
                List<Expression<Boolean>> expressions = predicate.getExpressions();
                if (subject.getType() != null) {
                    expressions.add(cb.equal(root.get("type").as(Integer.class), subject.getType()));
                }
                if (subject.getCreateTime() != null) {
                    expressions.add(cb.equal(root.get("createTime").as(DateTime.class), subject.getCreateTime()));
                }
                return predicate;
            }
        }, page);
    }

    @Override
    public String getTodaySubjectId(String subjectName) {
        String id = new String();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Subject> subjectRoot = cq.from(Subject.class);
        cq.select(cb.tuple(subjectRoot.get(subjectRoot.getModel().getSingularAttribute("id", String.class))));
        cq.where(
                cb.equal(subjectRoot.get(subjectRoot.getModel().getSingularAttribute("subjectName", String.class)), subjectName),
                cb.equal(subjectRoot.get(subjectRoot.getModel().getSingularAttribute("createTime", DateTime.class)), DateTime.parse(DateTime.now().toString("yyyy-MM-dd")))
        );

        TypedQuery<Tuple> q = em.createQuery(cq);
        List<Tuple> result = q.getResultList();

        for (Tuple tuple : result) {
            id = (String) tuple.get(0);
        }
        return id;

    }

    @Override
    public Subject saveOrUpdate(Subject subject) {
        return subjectRepository.save(subject);
    }

    @Override
    public Subject findOne(String id) {
        return subjectRepository.findOne(id);
    }

//    @Override
//    public void grabHotSubjectFromSogou() {
//        List<Subject> hotSubjects = this.findAll();
//        if(hotSubjects == null || hotSubjects.size()<=0){
//            Spider s = new Spider();
//            List<String> sogouHotSubjects = s.getHotSubject();
//            for(String sogouSb : sogouHotSubjects){
//                if(!hotSubjects.contains(sogouSb)) {
//                    Subject sb = new Subject();
//                    sb.setType(0);
//                    sb.setName(sogouSb);
//                    sb.setCreateTime(DateTime.now());
//                    subjectRepository.save(sb);
//                }
//            }
//        }
//    }


//    @Override
//    public void grabSubjectFromSogou() {
//        List<Subject> subjects = this.findAll();
//        if(subjects == null || subjects.size()<=0) {
//            Spider s = new Spider();
//            List<String> sogouHotSubjects = s.getSubject();
//            for (String sogouSb : sogouHotSubjects) {
//                if(!subjects.contains(sogouSb)){
//                    Subject sb = new Subject();
//                    sb.setType(1);
//                    sb.setName(sogouSb);
//                    sb.setCreateTime(DateTime.now());
//                    subjectRepository.save(sb);
//                }
//            }
//        }
//    }
}
