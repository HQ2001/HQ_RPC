package com.hlju.version1.server;

import com.hlju.version1.bean.User;
import com.hlju.version1.server.service.impl.UserServiceImpl;
import com.hlju.version1.service.UserService;

import java.io.*;
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
//                        System.out.println(111);
                        //读取要查询的id
                        Integer id = ois.readInt();
                        System.out.println("输入的id：" + id);
                        User user = userService.getUserById(id);
                        System.out.println("查询到的user：" + user);
                        //写回给客户端
                        oos.writeObject(user);
                        oos.flush();

                    } catch (IOException e) {
                        System.out.println("IO异常。。");
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
