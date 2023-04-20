package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.TranHistory;

public interface TranHistoryMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranHistory record);

    int insertSelective(TranHistory record);

    TranHistory selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranHistory record);

    int updateByPrimaryKey(TranHistory record);
}