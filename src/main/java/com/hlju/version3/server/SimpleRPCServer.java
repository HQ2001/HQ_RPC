package com.hlju.version3.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;

public class SimpleRPCServer implements RPCServer {

    // 封装服务的接口和实现类
    /**
     * key - interfaceName
     * value - 接口的实现类
     */
    private Map<String, Object> serviceProvider;

    public SimpleRPCServer(Map<String, Object> serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            //BIO阻塞处理请求
            while(true) {
                Socket client = server.accept();
                System.out.println("连接成功..");

                //开一个线程专门处理客户端请求
                new Thread(new WorkThread(client, serviceProvider)).start();

            }
        } catch (IOException e) {
            System.out.println("服务器启动失败。。");
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        System.out.println("服务器停止..");
    }
}
