package com.bonifacio.my_ksa_api.configuration.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
