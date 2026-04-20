package com.cuongtay.blog_app.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class PostUpdateForm {
    @NotBlank(message = "Tiêu đề không được để trống")
    @Length(max = 50, message = "Tiêu đề có tối đa 50 ký tự")
    private String title;
    @NotBlank(message = "mô tả không được để trống")
    @Length(max = 100, message = "mô tả có tối đa 100 ký tự")
    private String description;
    @NotBlank(message = "Nội dung không được để trống")
    @Length(max = 150, message = "Nội dung có tối đa 150 ký tự")
    private String content;

}
