package com.hlju.version7.client;

import com.hlju.version7.common.domain.RPCRequest;
import com.hlju.version7.common.domain.RPCResponse;

/**
 * 将RPCClient抽象成接口，和RPCServer一样
 */
public interface RPCClient {

    RPCResponse sendRequest(RPCRequest request);

}
