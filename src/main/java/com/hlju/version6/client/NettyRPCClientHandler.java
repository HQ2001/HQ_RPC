package com.hlju.version6.client;

import com.hlju.version6.common.domain.RPCResponse;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;

public class NettyRPCClientHandler extends SimpleChannelInboundHandler<RPCResponse> {
    // channel发生读事件
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCResponse response) throws Exception {
//        System.out.println("读取到：" + response);
        Channel channel = ctx.channel();

        // 给response设置别名，防止sendRequest中读取到别的（因为Netty是异步的）
        AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
        channel.attr(key).set(response);
        channel.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
