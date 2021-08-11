package com.hlju.version7.client;

import com.hlju.version7.common.domain.RPCRequest;
import com.hlju.version7.common.domain.RPCResponse;
import com.hlju.version7.common.register.ServiceRegister;
import com.hlju.version7.common.register.impl.ZkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class NettyRPCClient implements RPCClient {

    private static final NioEventLoopGroup eventLoopGroup;
    private static final Bootstrap bootstrap;
    private String host;
    private Integer port;
    private ServiceRegister serviceRegister;

    /**
     * netty客户端初始化
     */
    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new NettyRPCClientInitializer());
        System.out.println("Netty 客户端启动了。。");
    }

    public NettyRPCClient() {
        serviceRegister = new ZkServiceRegister();
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        // 调用请求时才进行host和port获取
        InetSocketAddress address = serviceRegister.serviceDiscovery(request.getInterfaceName());
        this.host = address.getHostName();
        this.port = address.getPort();
        ChannelFuture channelFuture = null;
        try {
            // 与服务器建立连接
            channelFuture = bootstrap.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            // 发送数据
            channel.writeAndFlush(request);
//            System.out.println("client发送完请求。。。");
            // 对关闭通道进行监听
            channel.closeFuture().sync();

            //因为netty是异步返回消息的，所以需要在接收时指定一个别名
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
//            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return RPCResponse.fail();
    }
}
