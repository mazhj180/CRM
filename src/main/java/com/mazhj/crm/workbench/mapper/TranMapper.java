package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.Tran;

public interface TranMapper {
    int deleteByPrimaryKey(String id);

    int insert(Tran record);

    int insertSelective(Tran record);

    Tran selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Tran record);

    int updateByPrimaryKey(Tran record);

    /**
     * 保存创建的交易
     * @param tran
     * @return
     */
    int insertTran(Tran tran);

    /**
     * 根据id查询交易的明细信息
     * @param id
     * @return
     */
    Tran selectTranForDetailById(String id);

    /**
     * 查询交易表中各个阶段的数据量
     * @return
     */
//    List<FunnelVO> selectCountOfTranGroupByStage();
}