package com.hlju.version6.service;

import com.hlju.version6.common.domain.User;

public interface UserService {

    User getUserById(Integer id);

    Integer insertUser(User user);

}
