package com.hlju.version2.service;

import com.hlju.version2.domain.User;

public interface UserService {

    User getUserById(Integer id);

    Integer insertUser(User user);

}
