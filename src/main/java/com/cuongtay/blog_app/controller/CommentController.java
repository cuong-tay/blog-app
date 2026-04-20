package com.cuongtay.blog_app.controller;

import com.cuongtay.blog_app.dto.CommentDto;
import com.cuongtay.blog_app.form.CommentCreateForm;
import com.cuongtay.blog_app.form.CommentFilterForm;
import com.cuongtay.blog_app.form.CommentUpdateForm;
import com.cuongtay.blog_app.service.CommentService;
import com.cuongtay.blog_app.validation.CommentIdExists;
import com.cuongtay.blog_app.validation.PostIdExists;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
public class CommentController {
    private CommentService commentService;

    @GetMapping("/api/v1/comments")
    public Page<CommentDto> findAll(CommentFilterForm form, Pageable pageable) {
        return commentService.findAll(form,pageable);
    }
    @GetMapping("/api/v1/posts/{postId}/comments")
    public Page<CommentDto> findByPostId(
            @PathVariable("postId") @PostIdExists Long postId, Pageable pageable) {
        return commentService.findByPostId(postId, pageable);
    }

    @GetMapping("/api/v1/comments/{id}")
    public CommentDto findById(
            @PathVariable @CommentIdExists Long id){
        return commentService.findById(id);
    }
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/posts/{postId}/comments")
    public CommentDto create(@RequestBody @Valid CommentCreateForm form,
                             @PathVariable("postId") @PostIdExists Long postId) {
        return commentService.create(form, postId);
    }

    @PutMapping("/api/v1/comments/{id}")
    public CommentDto update(
           @RequestBody @Valid CommentUpdateForm form,
           @PathVariable("id") @CommentIdExists Long id) {
        return commentService.update(form, id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/comments/{id}")
    public void delete(@PathVariable("id") @CommentIdExists Long id
    ) {
        commentService.delete(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/api/v1/posts/{postId}/comments")
    public void deleteAllByPostId(@PathVariable("postId") @PostIdExists Long postId) {
        commentService.deleteAllByPostId(postId);
    }
}
