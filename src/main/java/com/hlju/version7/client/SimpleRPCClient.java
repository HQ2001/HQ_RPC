package com.hlju.version7.client;

import com.hlju.version7.common.domain.RPCRequest;
import com.hlju.version7.common.domain.RPCResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SimpleRPCClient implements RPCClient {

    private String host;
    private Integer port;

    @Override
    public RPCResponse sendRequest(RPCRequest request) {
        return IOClient.sendRequest(host, port, request);
    }
}
