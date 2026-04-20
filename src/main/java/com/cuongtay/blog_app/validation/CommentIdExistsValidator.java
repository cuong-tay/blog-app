package com.cuongtay.blog_app.validation;

import com.cuongtay.blog_app.repository.CommentRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommentIdExistsValidator implements ConstraintValidator<CommentIdExists, Long> {

    private CommentRepository  commentRepository;
    // lược bỏ @Autowired do class chỉ có 1 constructor mặc định nên dùng lombok để tạo constructor
    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        return commentRepository.existsById(id);
    }
}
