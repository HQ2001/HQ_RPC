package com.hlju.version7.common.codeco;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hlju.version7.common.domain.RPCRequest;
import com.hlju.version7.common.domain.RPCResponse;

/**
 * 使用fastJson的方式，由于转成json字符串了，丢失了RPCResponse中的Object类型了，所以需要获取
 */
public class JsonSerializer implements Serializer {
    @Override
    public byte[] serialize(Object obj) {
        byte[] bytes = JSON.toJSONBytes(obj);
//        System.out.println(bytes);
        return bytes;
    }

    /**
     * 反序列化需要RPCRequest 和 RPCResponse做不同处理
     * @param bytes
     * @param messageType
     * @return
     */
    @Override
    public Object deSerialize(byte[] bytes, int messageType) {
//        System.out.println(bytes);
        Object obj = null;
        // 根据 messageType 做区分处理
        if (messageType == MessageType.RPC_REQUEST.getCode()) {
            RPCRequest request = JSON.parseObject(bytes, RPCRequest.class);
            // 对 参数列表和参数类型 做单独处理，因为Json只能反序列化基础类型
            Object[] params = new Object[request.getParams().length];
            for (int i = 0; i < params.length; i++) {
                Class<?> paramsType = request.getParamsType()[i];
//                System.out.println(paramsType);
                // 判断Json转的对象类型是否正确
                if (paramsType.isAssignableFrom(request.getParams()[i].getClass())){
                    params[i] = request.getParams()[i];
                } else {
                    // 将Json对象转化成Java对象
                    params[i] = JSON.toJavaObject((JSONObject) request.getParams()[i], paramsType);
                }
                request.setParams(params);
                obj = request;
            }
        } else if (messageType == MessageType.RPC_RESPONSE.getCode()) {
            // 为RPCResponse对象做如下处理
            RPCResponse response = JSON.parseObject(bytes, RPCResponse.class);
            Class<?> dataType = response.getDataType();
            // 如果类型不是dataType
            if (!dataType.isAssignableFrom(response.getData().getClass())){
                Object javaObject = JSON.toJavaObject((JSONObject) response.getData(), dataType);
                response.setData(javaObject);
            }
            obj = response;
        }
        return obj;
    }

    @Override
    public Integer getType() {
        return Serializer.JSON_SERIALIZER_TYPE;
    }
}
