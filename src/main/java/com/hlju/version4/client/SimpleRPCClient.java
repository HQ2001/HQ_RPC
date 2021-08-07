package com.hlju.version4.client;

import com.hlju.version4.domain.RPCRequest;
import com.hlju.version4.domain.RPCResponse;
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
