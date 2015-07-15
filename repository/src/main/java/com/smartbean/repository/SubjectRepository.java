package com.smartbean.repository;

import com.smartbean.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface SubjectRepository extends JpaRepository<Subject, String>, JpaSpecificationExecutor<Subject> {
    List<Subject> findAll();
}
