package com.cuongtay.blog_app.mapper;

import com.cuongtay.blog_app.dto.CommentDto;
import com.cuongtay.blog_app.entity.Comment;
import com.cuongtay.blog_app.form.CommentCreateForm;
import com.cuongtay.blog_app.form.CommentUpdateForm;

public class CommentMapper {
 public static CommentDto map(Comment comment) {
        var dto = new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());
        dto.setCreated_at(comment.getCreatedAt());
        dto.setUpdated_at(comment.getUpdatedAt());
        return dto;
 }
 public  static Comment map(CommentCreateForm commentCreateForm) {
        var dto = new Comment();
        dto.setName(commentCreateForm.getName());
        dto.setEmail(commentCreateForm.getEmail());
        dto.setBody(commentCreateForm.getBody());
        return dto;
 }
 public static void map(CommentUpdateForm commentUpdateForm, Comment comment) {
        comment.setName(commentUpdateForm.getName());
        comment.setEmail(commentUpdateForm.getEmail());
        comment.setBody(commentUpdateForm.getBody());
 }
}
