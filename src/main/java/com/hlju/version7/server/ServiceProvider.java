package com.hlju.version7.server;

import com.hlju.version7.common.register.ServiceRegister;
import com.hlju.version7.common.register.impl.ZkServiceRegister;
import lombok.Data;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 将存放 接口名和实现类 的map抽象成ServiceProvider类
 */
@Data
public class ServiceProvider {

    private Map<String, Object> serviceProvider;

    // 注册中心
    ServiceRegister serviceRegister;
    // 注册到注册中心时需要把自己的 host和端口号 暴漏
    private String host;
    private Integer port;

    public ServiceProvider(String host, Integer port) {
        serviceProvider = new HashMap<>();
        serviceRegister = new ZkServiceRegister();
        this.host = host;
        this.port = port;
    }

    /**
     * 向serviceProvider中添加新的服务
     * @param service
     */
    public void provideService(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            // 本机服务映射
            serviceProvider.put(clazz.getName(), service);
            // 注册中心进行注册
            this.serviceRegister.register(clazz.getName(), new InetSocketAddress(host, port));
        }
    }

    /**
     * 根据接口名获取某个服务
     * @param interfaceName
     * @return
     */
    public Object getService(String interfaceName) {
        return serviceProvider.get(interfaceName);
    }

}
