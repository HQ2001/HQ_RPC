package com.hlju.version1.client;

import com.hlju.version1.bean.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public class RPCClient {

    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", 10085);
//            Scanner scanner = new Scanner(System.in);
//            int id = scanner.nextInt();
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            int id = new Random().nextInt(Integer.MAX_VALUE);
            System.out.println("输入的id：" + id);
            //像服务器发请求
            oos.writeInt(id);
            oos.flush();
            //读取处理后结果
            User user = (User) ois.readObject();
            System.out.println("查询到：" + user);

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
