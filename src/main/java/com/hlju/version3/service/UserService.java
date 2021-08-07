package com.hlju.version3.service;

import com.hlju.version3.domain.User;

public interface UserService {

    User getUserById(Integer id);

    Integer insertUser(User user);

}
