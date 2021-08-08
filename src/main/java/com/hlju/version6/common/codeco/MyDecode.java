package com.hlju.version6.common.codeco;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * MyDecode 中反序列化时用到的序列化器根据传来的报文获取
 */
public class MyDecode extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
//        System.out.println("decode ....");
        // 获取messageType
        byte messageType = byteBuf.readByte();
        // 编码为 UNKNOWN 时表示处理不了，直接返回
        if (messageType == MessageType.UNKNOWN.getCode()) {
            System.out.println("不支持解码此类数据");
            return;
        }
        // 获取序列化器类型
        byte serializerType = byteBuf.readByte();
        // 根据类型获取序列化器，用于反序列化
        Serializer serializer = Serializer.getSerializerByCode((int) serializerType);
        // 根据 byteBuf.readInt() 获取序列化后的长度
        int readLength = byteBuf.readInt();
        byte[] bytes = new byte[readLength];
        // 读取序列化后的数据据到上面创建的 bytes[] 中
        byteBuf.readBytes(bytes);

        // 将数组反序列化并添加到Object列表中
        Object deSerialize = serializer.deSerialize(bytes, messageType);
        list.add(deSerialize);

    }
}
