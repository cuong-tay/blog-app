package com.cuongtay.blog_app.entity;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title",length = 50, nullable = false)
    private String title;
    @Column(name = "description",length = 100, nullable = false)
    private String description;
    @Column(name = "content",length = 150, nullable = false)
    private String content;
    @Column(name = "create_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;
    @Column(name = "update_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updateAt;
    // lưu ý phần mappedBy phải trùng với tên biến trong class Comment
    @OneToMany(mappedBy = "post")
    private List<Comment> comments;
}
