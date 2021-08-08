package com.hlju.version6.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor //使用Json序列化时需要
public class RPCResponse implements Serializable {

    // 状态码
    private Integer code;
    // 响应消息
    private String message;
    // 响应数据
    private Object data;

    // version5 中增加，用于标识返回的对象类型，不然JSON等序列化得不到对象
    private Class<?> dataType;

    /**
     * 返回成功的RPCResponse
     * @param data
     * @return
     */
    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).message("成功").data(data).dataType(data.getClass()).build();
    }

    /**
     * 返回失败的RPCResponse
     * @return
     */
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务器处理异常").build();
    }

}
