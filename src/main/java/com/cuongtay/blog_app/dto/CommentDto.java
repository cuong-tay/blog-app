package com.cuongtay.blog_app.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CommentDto {
    private Long id;
    private String name;
    private String email;
    private String body;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

}
