package com.hlju.version5.service;

import com.hlju.version5.common.domain.User;

public interface UserService {

    User getUserById(Integer id);

    Integer insertUser(User user);

}
