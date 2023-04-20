package com.mazhj.crm.settings.service;

import com.mazhj.crm.settings.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User login(Map<String,Object> paraMap);
    List<User> queryUsers();
}
