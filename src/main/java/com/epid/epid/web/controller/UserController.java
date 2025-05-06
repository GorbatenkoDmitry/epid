package com.epid.epid.web.controller;

import com.epid.epid.domain.user.User;
import com.epid.epid.domain.vaccinations.Vaccinations;
import com.epid.epid.service.UserService;
import com.epid.epid.service.VaccinationsService;
import com.epid.epid.web.dto.user.UserDto;
import com.epid.epid.web.dto.vaccinations.VaccinationsDto;
import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.dto.validation.OnUpdate;
import com.epid.epid.web.mappers.UserMapper;
import com.epid.epid.web.mappers.VaccinationsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Validated
        @Tag(name = 'Контроллер для работы с данными юзера', description = 'User API')
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;


    @PutMapping
    @PreAuthorize("@customSecurityExpression.canAccessUser(#dto.id)")
       public UserDto update(
            @Validated(OnUpdate.class) @RequestBody UserDto dto) {
        User user = userMapper.toEntity(dto);
        User updatedUser = userService.update(user);
        return userMapper.toDto(updatedUser);
    }

    @GetMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")

    public UserDto getById(@PathVariable Long id) {
        User user = userService.getById(id);
        return userMapper.toDto(user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("@customSecurityExpression.canAccessUser(#id)")
    public void deleteById(@PathVariable Long id) {
        userService.delete(id);
    }


}
