package com.cuongtay.blog_app.converter;

import com.cuongtay.blog_app.entity.User;
import jakarta.persistence.AttributeConverter;

public class UserRoleConverter implements AttributeConverter<User.Role,Character> {

    @Override
    public Character convertToDatabaseColumn(User.Role role) {
        return role.toString().charAt(0);
    }

    @Override
    public User.Role convertToEntityAttribute(Character character) {
        if (character =='A'){
            return User.Role.ADMIN;
        }
        if (character =='U'){
            return User.Role.USER;
        }
        return User.Role.MANAGER;
    }
}
