package com.cuongtay.blog_app.service;

import com.cuongtay.blog_app.dto.CommentDto;
import com.cuongtay.blog_app.form.CommentCreateForm;
import com.cuongtay.blog_app.form.CommentFilterForm;
import com.cuongtay.blog_app.form.CommentUpdateForm;
import com.cuongtay.blog_app.mapper.CommentMapper;
import com.cuongtay.blog_app.repository.CommentRepository;
import com.cuongtay.blog_app.repository.PostRepository;
import com.cuongtay.blog_app.specification.CommentSpecification;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {
    private CommentRepository commentRepository;
    private PostRepository postRepository;

    @Override
    public Page<CommentDto> findAll(CommentFilterForm form, Pageable pageable) {
        var spec = CommentSpecification.buildSpec(form);
        return commentRepository.findAll(spec, pageable).map(CommentMapper::map);
    }

    @Override
    public Page<CommentDto> findByPostId(Long postId, Pageable pageable) {
        return commentRepository.findByPostId(postId, pageable)
                .map(CommentMapper::map);
    }

    @Override
    public CommentDto findById(Long id) {
        return commentRepository.findById(id)
                .map(CommentMapper::map)
                .orElse(null);
    }

    @Override
    public CommentDto create(CommentCreateForm form, Long postId) {
        var optional = postRepository.findById(postId);
        if (optional.isEmpty()) {
            return null;
        }
        var post = optional.get();
        var comment = CommentMapper.map(form);
        comment.setPost(post);
        var savedComment = commentRepository.save(comment);
        return CommentMapper.map(savedComment);
    }
    @Override
    public CommentDto update(CommentUpdateForm form, Long id) {
        var optional = commentRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        var comment = optional.get();
        CommentMapper.map(form, comment);
        var savedComment = commentRepository.save(comment);
        return CommentMapper.map(savedComment);
    }
    @Override
    public void delete(Long id) {
        commentRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAllByPostId(Long postId) {
        commentRepository.deleteAllByPostId(postId);
    }

}
