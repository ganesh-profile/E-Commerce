package com.E_CommerceApplication.App.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class AppConfig {
    /**
     * Defines a ModelMapper bean.
     * ModelMapper is a powerful object mapping library that simplifies the conversion
     * of one object type to another (e.g., Entity to DTO and vice-versa).
     * By annotating this method with @Bean, Spring will automatically
     * create an instance of ModelMapper and register it in its application context,
     * making it available for injection using @Autowired in other components
     * like UserDetailsServiceImpl.
     *
     * @return A configured instance of ModelMapper.
     */


    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
