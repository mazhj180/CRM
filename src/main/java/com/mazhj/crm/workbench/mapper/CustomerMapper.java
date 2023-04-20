package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.Customer;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);

    /**
     * 保存创建的客户
     * @param customer
     * @return
     */
    int insertCustomer(Customer customer);

    /**
     * 查询所有的客户名称
     * @return
     */
    List<String> selectCustomerNameByName(String name);

    /**
     * 根据name精确查询客户
     * @param name
     * @return
     */
    Customer selectCustomerByName(String name);
}