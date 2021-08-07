package com.hlju.version4.client;

import com.hlju.version4.domain.RPCRequest;
import com.hlju.version4.domain.RPCResponse;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

public class NettyRPCClient implements RPCClient {

    private static final NioEventLoopGroup eventLoopGroup;
    private static final Bootstrap bootstrap;
    private String host;
    private Integer port;

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

    public NettyRPCClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
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
