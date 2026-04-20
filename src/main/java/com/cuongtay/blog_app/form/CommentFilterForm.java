package com.cuongtay.blog_app.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentFilterForm {
    private String search;
    private Long postId;
}
