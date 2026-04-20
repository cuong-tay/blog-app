package com.cuongtay.blog_app.service;

import com.cuongtay.blog_app.dto.UserDto;
import com.cuongtay.blog_app.entity.User;
import com.cuongtay.blog_app.form.UserCreateForm;
import com.cuongtay.blog_app.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService , UserDetailsService {


    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;// dùng để mã hóa mật khẩu
    private ModelMapper modelMapper;

    public UserDto create(UserCreateForm form){
        var user = modelMapper.map(form, User.class);
        var encodedPassword = passwordEncoder.encode(form.getPassword());
        user.setPassword(encodedPassword);// set mật khẩu đã mã hóa cho user

        var savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsernameOrEmail(username, username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username or email: " + username);
        }
        var role = user.getRole().name();//
        var authorities= AuthorityUtils.createAuthorityList(role);
        return new org.springframework.security.core.userdetails.User(
                username,
                user.getPassword(),
                authorities
        );
    }
}
