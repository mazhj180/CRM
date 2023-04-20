package com.mazhj.crm.workbench.mapper;

import com.mazhj.crm.workbench.pojo.ContactsActivityRelation;

import java.util.List;

public interface ContactsActivityRelationMapper {
    int deleteByPrimaryKey(String id);

    int insert(ContactsActivityRelation record);

    int insertSelective(ContactsActivityRelation record);

    ContactsActivityRelation selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ContactsActivityRelation record);

    int updateByPrimaryKey(ContactsActivityRelation record);

    /**
     * 批量保存创建的联系人和市场活动的关联关系
     * @param list
     * @return
     */
    int insertContactsActivityRelationByList(List<ContactsActivityRelation> list);
}