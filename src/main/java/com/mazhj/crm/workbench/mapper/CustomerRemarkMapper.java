package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.CustomerRemark;

import java.util.List;

public interface CustomerRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(CustomerRemark record);

    int insertSelective(CustomerRemark record);

    CustomerRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(CustomerRemark record);

    int updateByPrimaryKey(CustomerRemark record);

    /**
     * 批量保存创建的客户备注
     * @param list
     * @return
     */
    int insertCustomerRemarkByList(List<CustomerRemark> list);
}