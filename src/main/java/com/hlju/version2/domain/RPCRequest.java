package com.hlju.version2.domain;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
public class RPCRequest implements Serializable {

    // 请求服务接口名
    private String interfaceName;
    // 请求服务方法名
    private String methodName;
    // 参数列表
    private Object[] params;
    // 参数类型
    private Class<?>[] paramsType;

}
