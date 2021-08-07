package com.hlju.netty.simple;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {
        // 创建 BossGroup 和 WorkerGroup
        // bossGroup 只做处理链接请求，真正业务workerGroup处理
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        // 创建服务器启动对象，配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();

        // 使用链式编程设置
        bootstrap.group(bossGroup, workerGroup) //设置两个线程
                .channel(NioServerSocketChannel.class) //使用 NioServerSocketChannel 实现服务器通道
                .option(ChannelOption.SO_BACKLOG, 128) //设置线程队列得到连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true) //设置保持活动连接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { //创建一个通道初始化对象
                    // 给pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new NettyServerHandler());
                        System.out.println("初始化完成");
                    }
                }); //给workerGroup的EventLoop对应管道设置处理器
        System.out.println("服务器 is ready!");
        try {
            // 绑定端口，并同步，生成一个 ChannelFuture 对象
            // 启动服务器
            ChannelFuture cf = bootstrap.bind(10086).sync();

            // 对关闭通道进行监听
            cf.channel().closeFuture().sync();
        } catch (InterruptedException e) {
//            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

}
