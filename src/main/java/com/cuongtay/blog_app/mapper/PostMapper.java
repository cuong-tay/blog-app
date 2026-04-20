package com.cuongtay.blog_app.mapper;

import com.cuongtay.blog_app.dto.PostDto;
import com.cuongtay.blog_app.entity.Post;
import com.cuongtay.blog_app.form.PostCreateForm;
import com.cuongtay.blog_app.form.PostUpdateForm;

public class PostMapper {
    public static PostDto map(Post post) {
        var dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        dto.setCreateTime(post.getCreateAt());
        dto.setUpdateTime(post.getUpdateAt());
        return dto;
    }

    public  static Post map(PostCreateForm post) {
        var dto = new Post();
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());
        return dto;
    }

    public static  void  map(PostUpdateForm form, Post post) {
        post.setTitle(form.getTitle());
        post.setDescription(form.getDescription());
        post.setContent(form.getContent());
    }
}
