package com.hlju.version3.domain;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Blog implements Serializable {

    private Integer id;
    private Integer userId;
    private String title;

}
