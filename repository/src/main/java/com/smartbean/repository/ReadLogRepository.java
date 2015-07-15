package com.smartbean.repository;

import com.smartbean.entity.ReadLog;
import com.smartbean.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface ReadLogRepository extends JpaRepository<ReadLog, String>, JpaSpecificationExecutor<ReadLog> {
    public List<ReadLog> findByCustomerId(String customerId);

    public List<ReadLog> findByCustomerIdAndArticleId(String customerId, String articleId);

}
