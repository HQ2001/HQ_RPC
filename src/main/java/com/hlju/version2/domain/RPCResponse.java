package com.hlju.version2.domain;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
public class RPCResponse implements Serializable {

    // 状态码
    private Integer code;
    // 响应消息
    private String message;
    // 响应数据
    private Object data;

    /**
     * 返回成功的RPCResponse
     * @param data
     * @return
     */
    public static RPCResponse success(Object data) {
        return RPCResponse.builder().code(200).message("成功").data(data).build();
    }

    /**
     * 返回失败的RPCResponse
     * @return
     */
    public static RPCResponse fail() {
        return RPCResponse.builder().code(500).message("服务器处理异常").build();
    }

}
