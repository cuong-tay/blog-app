package com.cuongtay.blog_app.repository;

import com.cuongtay.blog_app.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface PostRepository
        extends JpaRepository<Post,Long>, JpaSpecificationExecutor<Post> {
    boolean existsByTitle(String title);
}
