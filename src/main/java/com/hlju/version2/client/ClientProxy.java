package com.hlju.version2.client;

import com.hlju.version2.domain.RPCRequest;
import com.hlju.version2.domain.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {

    String host;
    Integer port;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建RPCRequest
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes()).build();
        RPCResponse response = IOClient.sendRequest(host, port, request);
        return response.getData();
    }

    <T>T getClientProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }


}
