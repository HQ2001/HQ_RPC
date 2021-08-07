package com.hlju.version4.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServer implements RPCServer {

    private final ThreadPoolExecutor threadPool;
    // 封装服务的接口和实现类
    /**
     * key - interfaceName
     * value - 接口的实现类
     */
    private ServiceProvider serviceProvider;

    public ThreadPoolRPCServer(ServiceProvider serviceProvider) {
        this.threadPool = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000,
                60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100));
        this.serviceProvider = serviceProvider;
    }

    public ThreadPoolRPCServer(ServiceProvider serviceProvider,
                               int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime, TimeUnit unit,
                               ArrayBlockingQueue<Runnable> workQueue) {
        this.threadPool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket server = new ServerSocket(port);
            // BIO阻塞处理请求
            while (true) {
                Socket client = server.accept();
                System.out.println("连接成功");

                threadPool.execute(new WorkThread(client, this.serviceProvider));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {

    }
}
