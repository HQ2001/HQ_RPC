package com.hlju.version3.server;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 将存放 接口名和实现类 的map抽象成ServiceProvider类
 */
@Data
public class ServiceProvider {

    private Map<String, Object> serviceProvider;

    public ServiceProvider() {
        serviceProvider = new HashMap<>();
    }

    /**
     * 向serviceProvider中添加新的服务
     * @param service
     */
    public void provideService(Object service) {
        Class<?>[] interfaces = service.getClass().getInterfaces();
        for (Class<?> clazz : interfaces) {
            serviceProvider.put(clazz.getName(), service);
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
