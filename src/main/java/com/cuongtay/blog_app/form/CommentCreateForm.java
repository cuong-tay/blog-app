package com.cuongtay.blog_app.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class CommentCreateForm {
    @NotBlank(message = "Tên không được để trống")
    @Length(max = 50, message = "Tên có tối đa 50 ký tự")
     private String name;

    @Email
    @NotBlank(message = "Email không được để trống")
    @Length(max = 75, message = "Email có tối đa 75 ký tự")
     private String email;

    @NotBlank(message = "Nội dung bình luận không được để trống")
    @Length(max = 100, message = "Nội dung bình luận có tối đa 100 ký tự")
     private String body;

}
// @PositiveOrZero(message = "Id bài viết phải là số dương hoặc bằng 0")
// @PostIdExists // sử dụng custom annotation để kiểm tra xem postId có tồn tại
