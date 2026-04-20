package com.cuongtay.blog_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class BlogAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(BlogAppApplication.class, args);

        }
//    @Bean
//    public WebMvcConfigurer webMvcConfigurer() {
//        return new WebMvcConfigurer() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**")
//                        .allowedOrigins("*") // thay * bằng địa chỉ fontend không cần @CrossOrigin ở controller
//                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD");
//            }
//        };
//	}

}
