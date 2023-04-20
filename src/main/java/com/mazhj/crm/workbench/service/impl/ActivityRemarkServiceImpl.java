package com.mazhj.crm.workbench.service.impl;

import com.mazhj.crm.workbench.mapper.ActivityRemarkMapper;
import com.mazhj.crm.workbench.pojo.ActivityRemark;
import com.mazhj.crm.workbench.service.ActivityRemarkService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActivityRemarkServiceImpl implements ActivityRemarkService {
    private ActivityRemarkMapper activityRemarkMapper;

    public ActivityRemarkServiceImpl(ActivityRemarkMapper activityRemarkMapper) {
        this.activityRemarkMapper = activityRemarkMapper;
    }


    @Override
    public List<ActivityRemark> queryActivityRemarkForDetailByActivityId(String activityId) {
        List<ActivityRemark> activityRemarks = activityRemarkMapper.selectActivityRemarkForDetailByActivityId(activityId);
        return activityRemarks;
    }

    @Override
    public int saveCreateActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.insertActivityRemark(remark);
    }

    @Override
    public int deleteActivityRemarkById(String id) {
        return activityRemarkMapper.deleteActivityRemarkById(id);
    }

    @Override
    public int saveEditActivityRemark(ActivityRemark remark) {
        return activityRemarkMapper.updateActivityRemark(remark);
    }

}
