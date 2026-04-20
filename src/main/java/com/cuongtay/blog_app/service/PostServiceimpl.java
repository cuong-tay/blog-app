package com.cuongtay.blog_app.service;

import com.cuongtay.blog_app.dto.PostDto;
import com.cuongtay.blog_app.entity.Post;
import com.cuongtay.blog_app.form.PostCreateForm;
import com.cuongtay.blog_app.form.PostFilterForm;
import com.cuongtay.blog_app.form.PostUpdateForm;
import com.cuongtay.blog_app.mapper.PostMapper;
import com.cuongtay.blog_app.repository.PostRepository;
import com.cuongtay.blog_app.specification.PostSpecification;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//@Service("PostServiceimpl") // Qualifier đặt tên cụ thể cho bean dịch vụ
@Service
@Primary
@AllArgsConstructor
public class PostServiceimpl implements PostService {
    private PostRepository postRepository;
     private  ModelMapper modelMapper;// Sử dụng ModelMapper để chuyển đổi giữa Post và PostDto
    @Override
    public Page<PostDto> findAll(PostFilterForm form,Pageable pageable) {
        var spec= PostSpecification.buildSpec(form);
        return postRepository.findAll(spec,pageable)
                .map(post -> modelMapper
                        .map(post,PostDto.class)); // chuyển đổi danh sách Post thành danh sách PostDto sử dụng PostMapper
    }


    @Override
    public PostDto findById(Long id) {
        // tìm kiếm Post theo id và chuyển đổi thành PostDto nếu tồn tại, ngược lại trả về null
        return postRepository.findById(id).map(PostMapper::map).orElse(null);

    }
    @Override
    public PostDto create(PostCreateForm form) {
        var post = modelMapper.map(form, Post.class);
        var savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }

    @Override
    public PostDto update(PostUpdateForm form, Long id) {
        var optional= postRepository.findById(id);
        if (optional.isEmpty()) {
            return null;
        }
        var post = optional.get();
        modelMapper.map(form, post);
        var savedPost = postRepository.save(post);
        return modelMapper.map(savedPost, PostDto.class);
    }
    @Override
    public void delete(Long id) {
        postRepository.deleteById(id);
    }
}
