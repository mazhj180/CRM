package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.TranRemark;

import java.util.List;

public interface TranRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(TranRemark record);

    int insertSelective(TranRemark record);

    TranRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(TranRemark record);

    int updateByPrimaryKey(TranRemark record);

    /**
     * 批量保存创建的交易备注
     * @param list
     * @return
     */
    int insertTranRemarkByList(List<TranRemark> list);

    /**
     * 根据tranId查询该交易下所有备注的明细信息
     * @param tranId
     * @return
     */
    List<TranRemark> selectTranRemarkForDetailByTranId(String tranId);
}