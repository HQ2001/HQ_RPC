package com.hlju.version3.server;

/**
 * 将RPCServer抽象成接口
 */
public interface RPCServer {

    void start(int port);
    void stop();

}
