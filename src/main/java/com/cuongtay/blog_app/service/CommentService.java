package com.cuongtay.blog_app.service;

import com.cuongtay.blog_app.dto.CommentDto;
import com.cuongtay.blog_app.form.CommentCreateForm;
import com.cuongtay.blog_app.form.CommentFilterForm;
import com.cuongtay.blog_app.form.CommentUpdateForm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentService {
    Page<CommentDto> findAll(CommentFilterForm form, Pageable pageable);

    Page<CommentDto> findByPostId(Long postId, Pageable pageable);
    CommentDto findById(Long id);

    CommentDto create(CommentCreateForm form, Long postId);

    CommentDto update(CommentUpdateForm form, Long id);

    void delete(Long id);

    void deleteAllByPostId(Long postId);
}
