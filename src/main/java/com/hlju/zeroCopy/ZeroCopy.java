package com.hlju.zeroCopy;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ZeroCopy {

    public static void main(String[] args) {
        InetSocketAddress address = new InetSocketAddress(100086);
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(address);
            ByteBuffer buffer = ByteBuffer.allocate(4096);
            while (true) {
                SocketChannel socketChannel = serverSocketChannel.accept();
                int readCount = 0;
                while (readCount != -1) {
                    readCount = socketChannel.read(buffer);
                    // 倒带
                    buffer.rewind();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
