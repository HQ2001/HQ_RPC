package com.hlju.version6.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor //使用Json序列化时需要
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
