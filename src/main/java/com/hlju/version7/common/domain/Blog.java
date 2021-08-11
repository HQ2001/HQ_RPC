package com.hlju.version7.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor //使用Json序列化时需要
public class Blog implements Serializable {

    private Integer id;
    private Integer userId;
    private String title;

}
