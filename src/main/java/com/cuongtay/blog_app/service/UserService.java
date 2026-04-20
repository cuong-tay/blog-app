package com.cuongtay.blog_app.service;

import com.cuongtay.blog_app.dto.UserDto;
import com.cuongtay.blog_app.form.UserCreateForm;

public interface UserService {
     UserDto create(UserCreateForm form);
}
