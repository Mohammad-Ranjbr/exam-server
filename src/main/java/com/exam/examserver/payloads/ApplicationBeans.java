package com.exam.examserver.payloads;

import com.exam.examserver.entities.Role;
import com.exam.examserver.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationBeans {

    @Bean
    public ModelMapper getModelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Role getRole(){
        return new Role();
    }

    @Bean
    public RoleDto getRoleDto(){
        return new RoleDto();
    }

    @Bean
    public User getUser(){
        return new User();
    }

}
