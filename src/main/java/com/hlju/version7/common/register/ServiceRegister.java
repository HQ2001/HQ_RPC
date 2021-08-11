package com.hlju.version7.common.register;

import java.net.InetSocketAddress;

/**
 * 服务注册中心接口
 */
public interface ServiceRegister {

    // 用于注册服务
    void register(String serviceName, InetSocketAddress serverAddress);
    // 用于发现服务
    InetSocketAddress serviceDiscovery(String serviceName);

}
