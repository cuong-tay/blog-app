package com.cuongtay.blog_app.controller;

import com.cuongtay.blog_app.dto.UserDto;
import com.cuongtay.blog_app.form.UserCreateForm;
import com.cuongtay.blog_app.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@AllArgsConstructor
public class UserController {
    private UserService userService;

    @PostMapping("/api/v1/auth/register")
    public UserDto create(@RequestBody @Valid UserCreateForm form){
        return userService.create(form);
    }

}
