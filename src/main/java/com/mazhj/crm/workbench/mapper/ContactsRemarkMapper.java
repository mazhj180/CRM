package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.ContactsRemark;

import java.util.List;

public interface ContactsRemarkMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactsRemark record);

    int insertSelective(ContactsRemark record);

    ContactsRemark selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactsRemark record);

    int updateByPrimaryKey(ContactsRemark record);

    /**
     * 批量保存创建的联系人备注
     * @param list
     * @return
     */
    int insertContactsRemarkByList(List<ContactsRemark> list);
}