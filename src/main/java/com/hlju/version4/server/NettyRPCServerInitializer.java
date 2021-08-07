package com.hlju.version4.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NettyRPCServerInitializer extends ChannelInitializer<SocketChannel> {

    private ServiceProvider serviceProvider;

    // 对pipeline做一些初始化操作
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        // 自定义长度解码器，解决粘包问题，前四个字节作为标识长度的
        pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));

        // 将当前消息的长度写到前四个字节当中
        pipeline.addLast(new LengthFieldPrepender(4));

        // 使用java自带的序列化方式进行传输
        pipeline.addLast(new ObjectEncoder());
        pipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String className) throws ClassNotFoundException {
                return Class.forName(className);
            }
        }));
        // 添加handler
        pipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
