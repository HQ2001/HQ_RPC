package com.hlju.version3.server;

import com.hlju.version3.server.service.impl.UserServiceImpl;
import com.hlju.version3.server.service.impl.BlogServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TestServer {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
//        Map<String, Object> serviceProvider = new HashMap<>();
//        serviceProvider.put("com.hlju.version3.server.service.impl.UserServiceImpl", userService);
//        serviceProvider.put("com.hlju.version3.server.service.impl.BlogServiceImpl", blogService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideService(userService);
        serviceProvider.provideService(blogService);
        SimpleRPCServer simpleRPCServer = new SimpleRPCServer(serviceProvider.getServiceProvider());
        ThreadPoolRPCServer threadPoolRPCServer = new ThreadPoolRPCServer(serviceProvider.getServiceProvider());

//        simpleRPCServer.start(10085);
        threadPoolRPCServer.start(10085);

    }

}
