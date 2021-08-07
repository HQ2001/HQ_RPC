package com.hlju.version3.server.service.impl;

import com.hlju.version3.domain.User;
import com.hlju.version3.service.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserById(Integer id) {
        System.out.println("获取id为【" + id + "】的User。。");
        Random random = new Random();
        //模拟查询出User对象
        User user = User.builder().id(id)
                .name(UUID.randomUUID().toString().replace("-", "").substring(0, 10))
                .age(random.nextInt(100))
                .gender(random.nextBoolean()).build();
        return user;
    }

    @Override
    public Integer insertUser(User user) {
        System.out.println("向数据库插入：" + user);
        return 1;
    }
}
