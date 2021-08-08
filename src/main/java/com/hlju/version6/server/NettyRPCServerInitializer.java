package com.hlju.version6.server;

import com.hlju.version6.common.codeco.JsonSerializer;
import com.hlju.version6.common.codeco.MyDecode;
import com.hlju.version6.common.codeco.MyEncode;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyRPCServerInitializer extends ChannelInitializer<SocketChannel> {

    private ServiceProvider serviceProvider;

    // 对pipeline做一些初始化操作
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 添加自定义的编码器和解码器，Encode在前会导致无限循环
        pipeline.addLast(new MyDecode());
//        pipeline.addLast(new MyEncode(new ObjectSerializer()));
        // 使用Json序列化和反序列化
        pipeline.addLast(new MyEncode(new JsonSerializer()));
        // 添加handler
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
