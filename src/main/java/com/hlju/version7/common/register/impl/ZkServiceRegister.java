package com.hlju.version7.common.register.impl;

import com.hlju.version7.common.loadBanance.LoadBalance;
import com.hlju.version7.common.loadBanance.impl.RandomLoadBalance;
import com.hlju.version7.common.loadBanance.impl.RoundLoadBalance;
import com.hlju.version7.common.register.ServiceRegister;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

import java.net.InetSocketAddress;
import java.util.List;

public class ZkServiceRegister implements ServiceRegister {

    // Curator提供的Zookeeper客户端
    private CuratorFramework client;
    // 根节点路径
    private static final String ROOT_PATH = "H_RPC";

    // zookeeper客户端初始化
    public ZkServiceRegister() {
        // 指定重连策略，1000ms重连一次，最多重连3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        // 初始化client
        this.client = CuratorFrameworkFactory.builder().connectString("127.0.0.1:2181") //服务提供者或消费者都要连接这个地址进行
                .sessionTimeoutMs(60000) //会话超时60s算断开
                .retryPolicy(retryPolicy) //添加重试策略
                .namespace(ROOT_PATH).build();
        this.client.start();
        System.out.println("Zookeeper客户端启动。。");
    }

    @Override
    public void register(String serviceName, InetSocketAddress serverAddress) {
        try {
            // 若不存在该服务，将服务注册成永久节点，服务下线时，删除serverAddress，不删除节点
            if (client.checkExists().forPath("/" + serviceName) == null) {
                client.create().creatingParentContainersIfNeeded()
                        .withMode(CreateMode.PERSISTENT).forPath("/" + serviceName);
            }
            // 路径地址
            String path = "/" + serviceName + "/" + getServiceAddressStr(serverAddress);
            // 创建临时节点，服务下线就删除
            client.create().creatingParentContainersIfNeeded()
                    .withMode(CreateMode.EPHEMERAL).forPath(path);
        } catch (Exception e) {
            System.out.println("此服务已存在..");
        }
    }

    @Override
    public InetSocketAddress serviceDiscovery(String serviceName) {
        try {
            // 获取客户端下的serviceName这个服务
            List<String> services = client.getChildren().forPath("/" + serviceName);
            // 使用不同的负载均衡方式
//            LoadBalance loadBalance = new RandomLoadBalance();
            LoadBalance loadBalance = new RoundLoadBalance(); // 轮询
            String str = loadBalance.balance(services);
            return getInetSocketAddressByStr(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // 根据server的地址获取字符串  地址对象 -> xxx.xxx.xxx.xxx:port字符串
    private String getServiceAddressStr(InetSocketAddress serverAddress) {
        return serverAddress.getHostName() + ":" + serverAddress.getPort();
    }

    // 根据server的地址获取字符串，与上面方法相反
    private InetSocketAddress getInetSocketAddressByStr(String serverAddress) {
        String[] address = serverAddress.split(":");
        return new InetSocketAddress(address[0], Integer.parseInt(address[1]));
    }
}
