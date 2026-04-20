package com.cuongtay.blog_app.repository;

import com.cuongtay.blog_app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    long countByRole(User.Role role);

    User findByUsernameOrEmail(String username, String email);
}
