package com.hlju.version7.common.loadBanance.impl;

import com.hlju.version7.common.loadBanance.LoadBalance;

import java.util.List;

public class RoundLoadBalance implements LoadBalance {
    private int choose = 0;
    @Override
    public String balance(List<String> services) {
//        System.out.println("LoadBalance选择了：" + choose);
        return services.get(choose++ % services.size());
    }
}
