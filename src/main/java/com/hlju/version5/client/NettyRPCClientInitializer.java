package com.hlju.version5.client;

import com.hlju.version5.common.codeco.JsonSerializer;
import com.hlju.version5.common.codeco.MyDecode;
import com.hlju.version5.common.codeco.MyEncode;
import com.hlju.version5.common.codeco.ObjectSerializer;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyRPCClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 添加自定义的编码器和解码器，Encode在前会导致无限循环
        pipeline.addLast(new MyDecode());
//        pipeline.addLast(new MyEncode(new ObjectSerializer()));
        // 使用Json序列化和反序列化
        pipeline.addLast(new MyEncode(new JsonSerializer()));

        // 添加handler
        pipeline.addLast(new NettyRPCClientHandler());
    }
}
