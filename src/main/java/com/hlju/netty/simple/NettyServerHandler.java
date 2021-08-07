package com.hlju.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

/**
 * 自定义一个 handler 需要继承netty绑定好的某个ChannelInboundHandlerAdapter
 * 我们自定义的handler才能称为handler
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 读取事件（读取客户端发来的消息）

    /**
     *
     * @param ctx 上下文对象，含管道pipeline 和 通道channel
     * @param msg 客户端发送的数据，默认是Object
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程：" + Thread.currentThread().getName());
        System.out.println("server ctx : " + ctx);
        // 将msg转成ByteBuf
        // ByteBuf 是Netty提供的
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发来的消息是：" + ((ByteBuf) msg).toString(CharsetUtil.UTF_8));
        System.out.println("客户端的地址是：" + ctx.channel().remoteAddress());
    }

    // 数据读取完毕
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // write + flush 将数据加到缓存并刷新
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello，客户端", CharsetUtil.UTF_8));
    }

    // 处理异常
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭通道
        ctx.close();
    }
}
