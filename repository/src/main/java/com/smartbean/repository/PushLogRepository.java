package com.smartbean.repository;

import com.smartbean.entity.PushLog;
import com.smartbean.entity.ReadLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface PushLogRepository extends JpaRepository<PushLog, String>, JpaSpecificationExecutor<PushLog> {
    List<PushLog> findByCustomerIdAndTypeId(String customerId, String typeId);
}
