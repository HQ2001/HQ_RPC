package com.hlju.version2.client;

import com.hlju.version2.domain.User;
import com.hlju.version2.service.UserService;

public class RPCClient {

    public static void main(String[] args) {
        // 创建客户端代理对象
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 10085);
        UserService proxy = clientProxy.getClientProxy(UserService.class);

        // 调用服务中的getUserById方法
        User user = proxy.getUserById(10);
        System.out.println("查询到：" + user);

        // 调用服务中的
        Integer integer = proxy.insertUser(User.builder().id(100).name("郝强").age(20).gender(true).build());
        System.out.println("插入数据结果：" + integer);

    }

}
