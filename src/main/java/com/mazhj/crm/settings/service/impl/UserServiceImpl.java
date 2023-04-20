package com.mazhj.crm.settings.service.impl;

import com.mazhj.crm.settings.mapper.UserMapper;
import com.mazhj.crm.settings.pojo.User;
import com.mazhj.crm.settings.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;

    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public User login(Map<String, Object> paraMap) {
        User user = userMapper.selectUserByNameAndPwd(paraMap);
        return user;
    }

    @Override
    public List<User> queryUsers() {
        List<User> userList = userMapper.selectAllLegalUser();
        return userList;
    }
}
