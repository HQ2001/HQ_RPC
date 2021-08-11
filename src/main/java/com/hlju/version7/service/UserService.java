package com.hlju.version7.service;

import com.hlju.version7.common.domain.User;

public interface UserService {

    User getUserById(Integer id);

    Integer insertUser(User user);

}
