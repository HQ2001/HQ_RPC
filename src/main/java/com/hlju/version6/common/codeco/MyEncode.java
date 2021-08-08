package com.hlju.version6.common.codeco;

import com.hlju.version6.common.domain.RPCRequest;
import com.hlju.version6.common.domain.RPCResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import lombok.AllArgsConstructor;

/**
 * MyEncode 类中包含一个序列化器，用于编码时将对象序列化
 */
@AllArgsConstructor
public class MyEncode extends MessageToByteEncoder {

    private Serializer serializer;

    /**
     * 自定义编码，编码格式及长度：
     *    [编码类型(eg: RPCRequest)] [序列化类型(Serializer的类型)] [序列化后字节数组长度] [序列化后数据]
     *             1                            1                          4               n
     * @param ctx
     * @param o
     * @param byteBuf
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
//        System.out.println("encode：" + o.getClass());
        // 对RPCRequest 和 RPCResponse写入不同的MessageType
        if (o instanceof RPCRequest) {
            byteBuf.writeByte(MessageType.RPC_REQUEST.getCode());
        } else if (o instanceof RPCResponse) {
            byteBuf.writeByte(MessageType.RPC_RESPONSE.getCode());
        } else {
            System.out.println("不支持编码此类数据");
            byteBuf.writeByte(MessageType.UNKNOWN.getCode());
            return;
        }
        // 写入序列化器类型
        byteBuf.writeByte(serializer.getType());
        // 进行序列化
        byte[] serialize = serializer.serialize(o);
        // 写入数据长度
        byteBuf.writeInt(serialize.length);
        // 写入序列化后的对象
        byteBuf.writeBytes(serialize);
    }
}
