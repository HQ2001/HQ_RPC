package com.hlju.version6.server.service.impl;

import com.hlju.version6.common.domain.Blog;
import com.hlju.version6.service.BlogService;

public class BlogServiceImpl implements BlogService {
    @Override
    public Blog getBlogById(Integer id) {
        Blog blog = Blog.builder()
                .id(id).userId(11).title("我的个人博客").build();
        System.out.println("查询到了：" + blog);
        return blog;
    }
}
