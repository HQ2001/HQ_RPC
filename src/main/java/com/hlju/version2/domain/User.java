package com.hlju.version2.domain;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
public class User implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private Boolean gender;
}
