package com.github.huzhengen.configuration;

import com.github.huzhengen.mapper.UserMapper;
import com.github.huzhengen.service.OrderService;
import com.github.huzhengen.service.User;
import com.github.huzhengen.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JavaConfiguration {

    @Bean
    public OrderService orderService(UserService userService) {
        return new OrderService(userService);
    }

    @Bean
    public UserService userService(UserMapper userMapper) {
        return new UserService(userMapper);
    }

}