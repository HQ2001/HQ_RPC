package com.hlju.version6.common.codeco;

public interface Serializer {
    // Java原生的Object序列化编码为0
    static final Integer OBJECT_SERIALIZER_TYPE = 0;
    // JSON序列化编码为1
    static final Integer JSON_SERIALIZER_TYPE = 1;

    // 进行序列化，Object -> byte[]
    byte[] serialize(Object obj);

    // 进行反序列化，byte[] -> Object
    Object deSerialize(byte[] bytes, int messageType);

    // 返回当前序列化器的类型
    Integer getType();

    // 解码时 messageType 获取相应类型的序列化器
    static Serializer getSerializerByCode(Integer code) {
        if (code == OBJECT_SERIALIZER_TYPE) {
            return new ObjectSerializer();
        } else if (code == JSON_SERIALIZER_TYPE) {
            return new JsonSerializer();
        } else {
            return null;
        }
    }

}
