package com.hlju.version4.client;

import com.hlju.version4.domain.RPCRequest;
import com.hlju.version4.domain.RPCResponse;

/**
 * 将RPCClient抽象成接口，和RPCServer一样
 */
public interface RPCClient {

    RPCResponse sendRequest(RPCRequest request);

}
