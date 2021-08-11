package com.hlju.version7.common.loadBanance.impl;

import com.hlju.version7.common.loadBanance.LoadBalance;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance {
    @Override
    public String balance(List<String> services) {
        Random random = new Random();
        int choose = random.nextInt(services.size());
//        System.out.println("LoadBalance选择了：" + choose);
        return services.get(choose);
    }
}
