package com.hlju.version3.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean gender;
}
