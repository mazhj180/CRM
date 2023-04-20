package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ClueRemark record);

    int insertSelective(ClueRemark record);

    ClueRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ClueRemark record);

    int updateByPrimaryKey(ClueRemark record);

    /**
     * 根据clueId查询该线索下所有的备注
     * @param clueId
     * @return
     */
    List<ClueRemark> selectClueRemarkForDetailByClueId(String clueId);

    /**
     * 根据clueId查询该线索下所有的备注信息
     * @param clueId
     * @return
     */
    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    /**
     * 根据clueId删除该线索下所有的备注
     * @param clueId
     * @return
     */
    int deleteClueRemarkByClueId(String clueId);

    /**
     * 插入线索备注信息
     * @param remark
     * @return
     */
    int insertClueRemark(ClueRemark remark);

    int updateClueRemark(ClueRemark remark);

    int deleteClueRemarkById(String id);
}