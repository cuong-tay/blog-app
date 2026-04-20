package com.cuongtay.blog_app.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CommentIdExistsValidator.class) // truyền class validator vào đây
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface CommentIdExists {
    String message() default "Bình luận không tồn tại";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
