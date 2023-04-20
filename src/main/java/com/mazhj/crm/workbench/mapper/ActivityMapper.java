package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int deleteByPrimaryKey(String id);

    int insert(Activity record);

    int insertSelective(Activity record);

    Activity selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Activity record);

    int updateByPrimaryKey(Activity record);

    List<Activity> queryByConditionForPage(Map<String,Object> map);

    long queryCountOfByCondition(Map<String,Object> map);

    int deleteByIds(String[] ids);

    int update(Activity record);

    List<Activity> queryAll();

    List<Activity> queryByIds(String [] ids);

    /**
     * 批量保存创建的市场活动
     * @param activities
     * @return
     */
    int insertActivities(List<Activity> activities);

    /**
     * 根据id查询市场活动的明细信息
     * @param id
     * @return
     */
    Activity selectActivityForDetailById(String id);

    /**
     * 根据clueId查询该线索相关联的市场活动的明细信息
     * @param clueId
     * @return
     */
    List<Activity> selectActivityForDetailByClueId(String clueId);

    /**
     * 根据name模糊查询市场活动，并且把已经跟clueId关联过的市场活动排除
     * @param map
     * @return
     */
    List<Activity> selectActivityForDetailByNameClueId(Map<String,Object> map);

    /**
     * 根据ids查询市场活动的明细信息
     * @param ids
     * @return
     */
    List<Activity> selectActivityForDetailByIds(String[] ids);

    /**
     * 根据name模糊查询市场活动，并且查询那些跟clueId关联过的市场活动
     * @param map
     * @return
     */
    List<Activity> selectActivityForConvertByNameClueId(Map<String,Object> map);
}