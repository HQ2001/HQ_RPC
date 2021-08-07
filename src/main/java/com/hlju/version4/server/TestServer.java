package com.hlju.version4.server;

import com.hlju.version4.server.service.impl.BlogServiceImpl;
import com.hlju.version4.server.service.impl.UserServiceImpl;

public class TestServer {

    public static void main(String[] args) {

        UserServiceImpl userService = new UserServiceImpl();
        BlogServiceImpl blogService = new BlogServiceImpl();
//        Map<String, Object> serviceProvider = new HashMap<>();
//        serviceProvider.put("com.hlju.version4.server.service.impl.UserServiceImpl", userService);
//        serviceProvider.put("com.hlju.version4.server.service.impl.BlogServiceImpl", blogService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideService(userService);
        serviceProvider.provideService(blogService);


//        SimpleRPCServer simpleRPCServer = new SimpleRPCServer(serviceProvider);
//        ThreadPoolRPCServer threadPoolRPCServer = new ThreadPoolRPCServer(serviceProvider);

//        simpleRPCServer.start(10085);
//        threadPoolRPCServer.start(10085);
        NettyRPCServer nettyRPCServer = new NettyRPCServer(serviceProvider);
        nettyRPCServer.start(10086);


    }

}
