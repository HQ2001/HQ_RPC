package com.hlju.version7.common.loadBanance;

import java.util.List;

public interface LoadBalance {
    String balance(List<String> services);
}
