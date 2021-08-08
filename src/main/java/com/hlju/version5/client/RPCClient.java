package com.hlju.version5.client;

import com.hlju.version5.common.domain.RPCRequest;
import com.hlju.version5.common.domain.RPCResponse;

/**
 * 将RPCClient抽象成接口，和RPCServer一样
 */
public interface RPCClient {

    RPCResponse sendRequest(RPCRequest request);

}
