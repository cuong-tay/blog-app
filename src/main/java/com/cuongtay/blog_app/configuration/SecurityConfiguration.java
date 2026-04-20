package com.cuongtay.blog_app.configuration;

import com.cuongtay.blog_app.entity.User;
import com.cuongtay.blog_app.excaption.ErrorHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain SecurityFilterChain( // cấu hình màng lọc bảo mật
            HttpSecurity http, ErrorHandler errorHandler
    )throws Exception{
        http
                .csrf(AbstractHttpConfigurer::disable) // tắt CSRF để dễ dàng thử nghiệm API
                .cors(customizer -> {}) // không cho phép chia sẻ tài nguyên giữa các nguồn gốc khác nhau
                .sessionManagement(customizer ->
                        customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// không lưu trữ trạng thái phiên làm việc
                .exceptionHandling(customizer -> customizer
                        .authenticationEntryPoint(errorHandler) // xử lí lỗi xác thực
                        .accessDeniedHandler(errorHandler) // xử lí lỗi phân quyền
                )
                .authorizeHttpRequests(customizer -> customizer
                        .requestMatchers(HttpMethod.DELETE)
                        .hasAuthority(User.Role.ADMIN.name())
                        .requestMatchers(HttpMethod.PUT,"/api/v1/posts")
                        .hasAnyAuthority(User.Role.ADMIN.name(),User.Role.MANAGER.name())
                        .requestMatchers(HttpMethod.POST,"/api/v1/posts")
                        .hasAnyAuthority(User.Role.ADMIN.name(),User.Role.MANAGER.name())
                        .requestMatchers(HttpMethod.POST,"/api/v1/auth/register")
                        .permitAll() // cho phép truy cập không cần xác thực
                        .anyRequest() // ngoài đăng kí thì tất cả các yêu cầu khác đều cần xác thực
                        .authenticated() // đăng nhập
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // cấu hình CORS cho phép fontend truy cập API backend
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
       var configuration = new CorsConfiguration();
       configuration.addAllowedOrigin("http://localhost:3000");
       configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
       configuration.applyPermitDefaultValues();

       var source = new UrlBasedCorsConfigurationSource();
         source.registerCorsConfiguration("/**", configuration);
         return source;
    }
}
