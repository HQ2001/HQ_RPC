package com.hlju.version3.client;

import com.hlju.version3.domain.Blog;
import com.hlju.version3.domain.User;
import com.hlju.version3.service.BlogService;
import com.hlju.version3.service.UserService;

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

        // 创建客户端代理对象
        BlogService blogService = clientProxy.getClientProxy(BlogService.class);

        // 调用服务中的getBlogById方法
        Blog blog = blogService.getBlogById(10);
        System.out.println("查询到：" + blog);

    }

}
