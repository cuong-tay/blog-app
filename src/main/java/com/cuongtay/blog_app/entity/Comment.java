package com.cuongtay.blog_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
@Getter
@Setter
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "email", length = 100, nullable = false)
    private String email;

    @Column(name = "body", length = 100, nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    //@OneToOne
    @ManyToOne
    @JoinColumn(name = "post_id",
            referencedColumnName = "id",
            nullable = false
         // unique = true // nếu một bài viết chỉ có một comment thì dùng unique=true
    )
    @OnDelete(action = OnDeleteAction.CASCADE) // khi xóa bài viết thì xóa luôn comment liên quan
    private Post post;
}
