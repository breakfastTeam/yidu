package com.smartbean.service;

import com.smartbean.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
public interface SubjectService {
    public List<Subject> findAll();

    /**
     * 获取所有符合条件的话题
     * **/
    public Page<Subject> findAll(Pageable page, Subject subject);

    /**
     * 获取今日某一话题主键
     * **/
    public String getTodaySubjectId(String subjectName);

    /**
     * 保存分类
     * **/
    public Subject saveOrUpdate(Subject subject);

    /**
     * 保存分类
     * **/
    public Subject findOne(String id);


}
