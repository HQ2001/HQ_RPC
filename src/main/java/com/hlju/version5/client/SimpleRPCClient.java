package com.hlju.version5.client;

import com.hlju.version5.common.domain.RPCRequest;
import com.hlju.version5.common.domain.RPCResponse;
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
