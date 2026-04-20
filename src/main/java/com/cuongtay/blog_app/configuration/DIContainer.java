package com.cuongtay.blog_app.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DIContainer {
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON) // giúp tạo một instance duy nhất cho toàn bộ ứng dụng
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
