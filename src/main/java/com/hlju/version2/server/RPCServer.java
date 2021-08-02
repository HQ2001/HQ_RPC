package com.hlju.version2.server;

import com.hlju.version2.domain.RPCRequest;
import com.hlju.version2.domain.RPCResponse;
import com.hlju.version2.domain.User;
import com.hlju.version2.server.service.impl.UserServiceImpl;
import com.hlju.version2.service.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        try {
            ServerSocket server = new ServerSocket(10085);
            //BIO阻塞处理请求
            while(true) {
                Socket client = server.accept();
                System.out.println("连接成功..");

                //开一个线程专门处理客户端请求
                new Thread(() -> {
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
                        ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
                        //读取RPCRequest对象
                        RPCRequest rpcRequest = (RPCRequest)ois.readObject();
                        Method method = userService.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamsType());
                        Object invoke = method.invoke(userService, rpcRequest.getParams());
                        //将封装后RPCResponse写回给客户端
                        oos.writeObject(RPCResponse.success(invoke));
                        oos.flush();

                    } catch (IOException | ClassNotFoundException e) {
                        System.out.println("IO异常。。");
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        } catch (IOException e) {
            System.out.println("服务器启动失败。。");
            e.printStackTrace();
        }


    }

}
