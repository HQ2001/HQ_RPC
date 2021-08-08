package com.hlju.version5.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyRPCServer implements RPCServer {

    // 封装服务的接口和实现类
    /**
     * key - interfaceName
     * value - 接口的实现类
     */
    private ServiceProvider serviceProvider;

    @Override
    public void start(int port) {
        // netty服务线程组，bossGroup 和 workGroup
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        System.out.println("netty服务器启动了。。。");
        // netty服务器启动器
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            // 初始化启动器
            serverBootstrap.group(bossGroup, workGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new NettyRPCServerInitializer(serviceProvider));
            // 同步监听
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() {
        System.out.println("服务器关闭...");
    }
}
