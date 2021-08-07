package com.hlju.version4.service;

import com.hlju.version4.domain.User;

public interface UserService {

    User getUserById(Integer id);

    Integer insertUser(User user);

}
