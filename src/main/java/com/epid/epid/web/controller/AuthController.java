package com.epid.epid.web.controller;

import com.epid.epid.domain.user.User;
import com.epid.epid.service.AuthService;
import com.epid.epid.service.UserService;
import com.epid.epid.web.dto.auth.JwtRequest;
import com.epid.epid.web.dto.auth.JwtResponse;
import com.epid.epid.web.dto.user.UserDto;
import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.mappers.UserMapper;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
//добовляем аннотацию рест контроллер рест потому что это рест приложение, а не с фронтом
@RestController
//реквест маппинг аннотация для маппинга запросов, в аппликэйшн конфиг у нас настройка спринг секьюрити,
//которая доступна для всех пользоватлей без авторизации
@RequestMapping("/api/v1/auth")
//RequiredArgsConstructor позволит получить конструктор с параметром для каждого поля, но эти параметры потребуют специальной обработки.
@RequiredArgsConstructor
//Аннотация @Validated активирует механизм валидации Spring на уровне методов.
@Validated
public class AuthController {
//создаем переменные файнал сервисов и мапперов нужных нам для логики класса и делаем их фанал, что бы они заавтовайрислись
    private final AuthService authService;
    private final UserService userService;
    private final UserMapper userMapper;
//далее вещаем аннотацию постмапиин на пост запрос на авторизацию
    @PostMapping("/login")
//создаем метод с возвращаеммым типом результата JwtResponse (которая содержит айди юзернейм и два токена)
    public JwtResponse login(
            //в параметер передаем переменную  JwtRequest класс который возвращает результат проверки логина
            // пароля и записываем ее в переменную loginRequest
            //вещаем на параметр аннотацию @Validated, которая активирует механизм валидации Spring на уровне методов.
          //Аннотация @RequestBody обеспечивает доступ к телу HTTP-запроса.
            @Validated @RequestBody final JwtRequest loginRequest
    ) {//передаем результат в метод логин класса сервиса авторизации
        return authService.login(loginRequest);
    }
//делаем логику на пост запрос регистрации
    @PostMapping("/register")
    //после проверки возвращаются данные в типа дто это параметры введенные пользователем
    public UserDto register(
            //в параметр передаем данные в дто, которые ввел пользователь на отправку
        //вещаем на параметр аннотацию @Validated, которая активирует механизм валидации Spring на уровне методов.
            //вещаем метки на OnCreate и в классе бзер дто с этой меткой идут почти все поля, кроме айди
            @Validated(OnCreate.class)
        //Аннотация @RequestBody обеспечивает доступ к телу HTTP-запроса.
        @RequestBody final UserDto userDto
    ) {
         //достаем данные введенные пользователем и приводим эти дто данные к ентити класса юзер через юзер мэппер
        User user = userMapper.toEntity(userDto);
        //создаем метод создания юзера и вызываем метод юзер сервиса на создание и передаем приведенные данные
        User createdUser = userService.create(user);
        // и теперь обратно возвращаем результат createdUser (данные юзера) и переводит их к дто
        return userMapper.toDto(createdUser);
    }
// тут создаем на обновление страницы  ??? по запросу рефреш
    @PostMapping("/refresh")
    //создаем метод рефреш с типом возвращаемого значения JwtResponse
    public JwtResponse refresh(
            // в параметры передаем данные строку, которая есть у пользователя - рефрештокен
            @RequestBody final String refreshToken
    ) { //фрефреш токен передаем в метод рефреш сервиса авторизации
        return authService.refresh(refreshToken);
    }

}