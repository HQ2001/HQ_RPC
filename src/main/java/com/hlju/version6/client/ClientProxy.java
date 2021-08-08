package com.hlju.version6.client;

import com.hlju.version6.common.domain.RPCRequest;
import com.hlju.version6.common.domain.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {

    private RPCClient client;

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建RPCRequest
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args)
                .paramsType(method.getParameterTypes()).build();
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    <T>T getClientProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }


}
