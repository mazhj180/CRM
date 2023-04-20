package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueActivityRelation record);

    int insertSelective(ClueActivityRelation record);

    ClueActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueActivityRelation record);

    int updateByPrimaryKey(ClueActivityRelation record);

    /**
     * 批量保存创建的线索和市场活动的关联关系
     * @param list
     * @return
     */
    int insertClueActivityRelationByList(List<ClueActivityRelation> list);

    /**
     * 根据clueId和activityId删除线索和市场活动的关联关系
     * @param relation
     * @return
     */
    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);

    /**
     * 根据clueId查询线索和市场活动的关联关系
     * @param clueId
     * @return
     */
    List<ClueActivityRelation> selectClueActivityRelationByClueId(String clueId);

    /**
     * 根据clueId删除线索和市场活动的关联关系
     * @param clueId
     * @return
     */
    int deleteClueActivityRelationByClueId(String clueId);
}