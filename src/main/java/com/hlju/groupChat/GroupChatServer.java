package com.hlju.groupChat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;

public class GroupChatServer {


    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

    private Selector selector;
    // 用来做监听的
    private ServerSocketChannel listenChannel;
    private static final Integer PORT = 10085;

    public GroupChatServer() {
        try {
            // 得到选择器
            this.selector = Selector.open();
            // ServerSocketChannel
            this.listenChannel = ServerSocketChannel.open();
            // 绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            // 设置非阻塞
            listenChannel.configureBlocking(false);
            // 将该channel注册到selector中
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 监听
    public void listen() {
        //循环处理
        try {
            while (true) {
                // 监听两秒
                int count = selector.select();

                // 有事件需要处理
                if (count > 0) {
                    // 遍历selectionKey集合
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();

                        // 监听accept
                        if (key.isAcceptable()) {
                            SocketChannel sc = listenChannel.accept();
                            // 设置非阻塞
                            sc.configureBlocking(false);
                            // 将该sc注册到selector中
                            sc.register(selector, SelectionKey.OP_READ);
                            // 提示上线
                            System.out.println(sc.getRemoteAddress() + " 上线 ");
                        }

                        // 通道发生read事件，即通道是可读状态
                        if (key.isReadable()) {
                            // 处理读客户端数据，专门写方法
                            readData(key);
                        }
                        iterator.remove();
                    }
                } else {
                    System.out.println("等待。。。");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key) {

        // 定义一个SocketChannel
        SocketChannel channel = null;
        try {
            // 得到channel
            channel = (SocketChannel) key.channel();
            // 创建缓冲
            ByteBuffer buffer = ByteBuffer.allocate(1024);

            int count = channel.read(buffer);
            // 根据count的值做处理
            if (count > 0) {
                String msg = new String(buffer.array());
                // 输出该消息
                System.out.println("from客户端：" + msg);

                // 向其他客户端发送消息(去掉自己)
                sendToOtherClients(msg, channel);
            }
        } catch (IOException e) {
            try {
                System.out.println(channel.getRemoteAddress() + " 离线了。。");
                // 取消注册
                key.cancel();
                // 关闭通道
                channel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }

    }

    private void sendToOtherClients(String msg, SocketChannel channel) throws IOException {
        System.out.println("服务器转发消息中");
        // 遍历所有注册到selector中的，并排除自己
        for (SelectionKey key : selector.keys()) {
            // 取出通道
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != channel) {
                // 转型
                SocketChannel dest = (SocketChannel) targetChannel;
                // 将msg存储到buffer
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                // 将buffer的数据写入到通道
                dest.write(buffer);

            }
        }
    }

}
