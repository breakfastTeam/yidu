package com.smartbean.service.impl;

import com.smartbean.entity.ShareLog;
import com.smartbean.repository.ShareLogRepository;
import com.smartbean.service.ShareLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by Administrator on 2015/6/24.
 */
@Service("shareLogService")
@Transactional
public class ShareLogServiceImpl implements ShareLogService {

    @Autowired
    private ShareLogRepository shareLogRepository;

    @Override
    public ShareLog save(ShareLog shareLog) {
        return shareLogRepository.save(shareLog);
    }

}
