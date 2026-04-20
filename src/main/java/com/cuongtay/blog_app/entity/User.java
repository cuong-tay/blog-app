package com.cuongtay.blog_app.entity;

import com.cuongtay.blog_app.converter.UserRoleConverter;
import com.cuongtay.blog_app.generator.UserIdGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcType;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.descriptor.jdbc.CharJdbcType;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    // cách 1 : dùng Generation Type.IDENTITY để tự động tăng id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // cách 2 : dùng Sequence để
//    @SequenceGenerator(
//            name = "user_id_generator",
//            sequenceName = "user_id_sequence",
//            initialValue = 10,
//            allocationSize = 1
//    )
//    @GeneratedValue(generator = "user_id_generator")
    // cách 3: dùng UUID
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @JdbcType(value = CharJdbcType.class) // lưu uuid dưới dạng chuỗi ký tự trong db
//    private UUID id;
    // cách 4: dùng custom generator
//    @GenericGenerator(
//            name ="user_id_generator",
//            type = UserIdGenerator.class
//    )
//    @GeneratedValue(generator = "user_id_generator")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;
    @Column(name = "username", length = 50, nullable = false, unique = true)
    private String username;

    @Column(name = "password", length = 100, nullable = false)
    private String password;
    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime created_at;

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    private LocalDateTime updated_at;

    @Column(name = "role", nullable = false)
    //@Enumerated(value=EnumType.ORDINAL)// lưu theo số thứ tự 0,1,2
    //@Enumerated(value=EnumType.STRING)
    @Convert(converter = UserRoleConverter.class) // custom được tất cả các kiểu lưu trữ kể enums,...
    private Role role;

    public enum Role{
        ADMIN,USER,MANAGER
    }


}
