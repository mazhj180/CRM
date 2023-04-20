package com.mazhj.crm.workbench.service;

import com.mazhj.crm.workbench.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {

    int save(Activity activity);

    List<Activity> queryForPage(Map<String,Object> map);

    long countOf(Map<String,Object> map);

    int deleteByIds(String [] ids);

    int modify(Activity activity);

    Activity selectByID(String id);

    List<Activity> queryAll();

    List<Activity> queryByIds(String[] ids);

    int importActivities(List<Activity> activities);

    Activity queryActivityForDetailById(String id);

    List<Activity> queryActivityForDetailByClueId(String clueId);

    List<Activity> queryActivityForDetailByNameClueId(Map<String,Object> map);

    List<Activity> queryActivityForDetailByIds(String[] ids);

    List<Activity> queryActivityForConvertByNameClueId(Map<String,Object> map);
}
