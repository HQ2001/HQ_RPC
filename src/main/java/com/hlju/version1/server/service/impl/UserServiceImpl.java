package com.hlju.version1.server.service.impl;

import com.hlju.version1.bean.User;
import com.hlju.version1.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
//        System.out.println("获取id为【" + id + "】的User。。");
        Random random = new Random();
        //模拟查询出User对象
        User user = new User(id,
                UUID.randomUUID().toString().replace("-", "").substring(0, 10),
                random.nextInt(100),
                random.nextBoolean());
        return user;
    }
}
