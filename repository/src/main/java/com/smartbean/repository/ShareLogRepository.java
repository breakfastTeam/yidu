package com.smartbean.repository;

import com.smartbean.entity.ReadLog;
import com.smartbean.entity.ShareLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by Administrator on 2015/6/24.
 */
public interface ShareLogRepository extends JpaRepository<ShareLog, String>, JpaSpecificationExecutor<ShareLog> {

}
