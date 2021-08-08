package com.hlju.version5.common.codeco;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MessageType {
    RPC_REQUEST(0),
    RPC_RESPONSE(1),
    UNKNOWN(-1)
    ;

    private int code;

    public int getCode() {
        return code;
    }
}
