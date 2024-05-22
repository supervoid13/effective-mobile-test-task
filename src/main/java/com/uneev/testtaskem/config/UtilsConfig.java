package com.uneev.testtaskem.config;

import com.uneev.testtaskem.dto.UserRegistrationDto;
import com.uneev.testtaskem.dto.UserResponseDto;
import com.uneev.testtaskem.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class UtilsConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<UserRegistrationDto, User> userRegMapper = modelMapper.createTypeMap(
                UserRegistrationDto.class,
                User.class
        );
        userRegMapper.addMappings(mapper -> mapper.skip(User::setPassword));

        TypeMap<User, UserResponseDto> userResponseMapper = modelMapper.createTypeMap(
                User.class,
                UserResponseDto.class
        );
        userResponseMapper.addMappings(mapper -> mapper.map(src ->
                src.getBankAccount().getDetails(), UserResponseDto::setBankAccountDetails));

        return modelMapper;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
