package com.hlju.version6.common.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor //使用Json序列化时需要
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean gender;
}
