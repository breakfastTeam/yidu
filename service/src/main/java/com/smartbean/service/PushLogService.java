package com.smartbean.service;

import com.smartbean.entity.PushLog;
import com.smartbean.entity.ReadLog;

import java.util.List;

/**
 * Created by Administrator on 2015/6/28.
 */
public interface PushLogService {

    public PushLog save(PushLog pushLog);

    public List<PushLog> save(List<PushLog> pushLogs);

    public boolean isPushed(String customerId, String articleId);

    /**
     * 获取客户下已经推送过的文章ID
     * **/
    public List<String> getPushedArticlesId(String customerId);
}
