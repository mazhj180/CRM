package com.mazhj.crm.settings.mapper;

import com.mazhj.crm.settings.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User selectUserByNameAndPwd(Map<String,Object> map);

    List<User> selectAllLegalUser();
}