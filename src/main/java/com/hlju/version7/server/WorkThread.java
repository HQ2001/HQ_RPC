package com.hlju.version7.server;

import com.hlju.version7.common.domain.RPCRequest;
import com.hlju.version7.common.domain.RPCResponse;
import lombok.AllArgsConstructor;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

@AllArgsConstructor
public class WorkThread implements Runnable {

    private Socket client;
    private ServiceProvider serviceProvider;

    @Override
    public void run() {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            // 读取RPCRequest对象(抽取成一个方法)
            RPCRequest rpcRequest = (RPCRequest) ois.readObject();

            // 获取响应
            RPCResponse response = getResponse(rpcRequest);

            // 将封装后RPCResponse写回给客户端
            oos.writeObject(response);
            oos.flush();

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("IO异常。。");
            e.printStackTrace();
        }
    }

    private RPCResponse getResponse(RPCRequest request) {
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        Method method = null;
        try {
            //反射获取调用的方法
            method = service.getClass().getMethod(request.getMethodName(), request.getParamsType());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return RPCResponse.fail();
    }

}
