package com.uneev.testtaskem.controller;

import com.uneev.testtaskem.config.UserSpecification;
import com.uneev.testtaskem.dto.UserParamsDto;
import com.uneev.testtaskem.dto.UserResponseDto;
import com.uneev.testtaskem.entity.User;
import com.uneev.testtaskem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;
    private final UserSpecification userSpecification;

    @GetMapping("")
    public List<UserResponseDto> retrieveAllUsersBySpecOnPage(
            UserParamsDto paramsDto,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        System.out.println(paramsDto);
        Specification<User> specification = userSpecification.build(paramsDto);

        List<User> users = userService.getAllBySpecOnPage(page, size, specification);

        return modelMapper.map(users, new TypeToken<List<UserResponseDto>>(){}.getType());
    }

}
