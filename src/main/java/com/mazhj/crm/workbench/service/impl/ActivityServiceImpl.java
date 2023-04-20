package com.mazhj.crm.workbench.service.impl;

import com.mazhj.crm.workbench.mapper.ActivityMapper;
import com.mazhj.crm.workbench.pojo.Activity;
import com.mazhj.crm.workbench.service.ActivityService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ActivityServiceImpl implements ActivityService {


    private ActivityMapper activityMapper;

    public ActivityServiceImpl(ActivityMapper activityMapper){
        this.activityMapper = activityMapper;
    }

    @Override
    public int save(Activity activity) {
        int res = activityMapper.insert(activity);
        return res;
    }

    @Override
    public List<Activity> queryForPage(Map<String, Object> map) {
        List<Activity> activities = activityMapper.queryByConditionForPage(map);
        return activities;
    }

    @Override
    public long countOf(Map<String, Object> map) {
        long counts = activityMapper.queryCountOfByCondition(map);
        return counts;
    }

    @Override
    public int deleteByIds(String[] ids) {
        int res = activityMapper.deleteByIds(ids);
        return res;
    }

    @Override
    public int modify(Activity activity) {
        int res = activityMapper.update(activity);
        return res;
    }

    @Override
    public Activity selectByID(String id) {
        Activity activity = activityMapper.selectByPrimaryKey(id);
        return activity;
    }

    @Override
    public List<Activity> queryAll() {
        List<Activity> activities = activityMapper.queryAll();
        return activities;
    }

    @Override
    public List<Activity> queryByIds(String[] ids) {
        List<Activity> activities = activityMapper.queryByIds(ids);
        return activities;
    }

    @Override
    public int importActivities(List<Activity> activities) {
        int res = activityMapper.insertActivities(activities);
        return res;
    }

    @Override
    public Activity queryActivityForDetailById(String id) {
        Activity activity = activityMapper.selectActivityForDetailById(id);
        return activity;
    }

    @Override
    public List<Activity> queryActivityForDetailByClueId(String clueId) {
        return activityMapper.selectActivityForDetailByClueId(clueId);
    }

    @Override
    public List<Activity> queryActivityForDetailByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForDetailByNameClueId(map);
    }

    @Override
    public List<Activity> queryActivityForDetailByIds(String[] ids) {
        return activityMapper.selectActivityForDetailByIds(ids);
    }

    @Override
    public List<Activity> queryActivityForConvertByNameClueId(Map<String, Object> map) {
        return activityMapper.selectActivityForConvertByNameClueId(map);
    }
}
