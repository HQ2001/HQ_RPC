package com.hlju.version6.client;

import com.hlju.version6.common.domain.RPCRequest;
import com.hlju.version6.common.domain.RPCResponse;

/**
 * 将RPCClient抽象成接口，和RPCServer一样
 */
public interface RPCClient {

    RPCResponse sendRequest(RPCRequest request);

}
