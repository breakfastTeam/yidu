package com.smartbean.service;

import com.smartbean.entity.ReadLog;
import com.smartbean.entity.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
public interface ReadLogService {

    public ReadLog save(ReadLog readLog);

    public boolean isRead(String customerId, String articleId);

    public List<String> getReadArticlesId(String customerId);
}
