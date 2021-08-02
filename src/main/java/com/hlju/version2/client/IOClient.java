package com.hlju.version2.client;

import com.hlju.version2.domain.RPCRequest;
import com.hlju.version2.domain.RPCResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * 负责底层发送RPCRequest和接收RPCResponse
 */
public class IOClient {

    public static RPCResponse sendRequest(String host, Integer port, RPCRequest request) {
        Socket client = null;
        try {
            client = new Socket(host, port);
            ObjectOutputStream oos = new ObjectOutputStream(client.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(client.getInputStream());
            // 向服务器发请求
            oos.writeObject(request);
            oos.flush();
            // 读取处理后结果并返回
            RPCResponse response = (RPCResponse) ois.readObject();
            return response;
        } catch (IOException e) {
            System.out.println("IO异常。。");
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFound。。");
            e.printStackTrace();
            return null;
        } finally {
            if (client != null) {
                try {
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
