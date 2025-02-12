package com.epid.epid.service.impl;

import com.epid.epid.domain.user.User;
import com.epid.epid.service.AuthService;
import com.epid.epid.service.UserService;
import com.epid.epid.web.dto.auth.JwtRequest;
import com.epid.epid.web.dto.auth.JwtResponse;
import com.epid.epid.web.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

// сервис отвечает за авторизацию пользователя и обновление рефреш токена
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
//сначала автовайрим менеджер аунтификации, что бы авторизовать пользователя
// сревиса юзера что бы достать юзера по юзеренйму
// и токена провайдера,
// который создает токена провойдара что бы создать токены
    // все это вернем в JwtResponse
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;
// создаем метод логгирования типа JwtResponse, который содержит айди нейм и токены
    @Override
    public JwtResponse login(
            // передаем данные JwtRequest логин с паролем, что бы работать с ними и записываем в loginRequest
            final JwtRequest loginRequest
    ) {
        // теперь логика метода создаем объект JwtResponse, который содержит айди нейм и токены
        JwtResponse jwtResponse = new JwtResponse();

        //вызываем метод authenticate из authenticationManager, это класс спринг секьюрити
        authenticationManager.authenticate(
                ///  в этом методе создаем объект UsernamePasswordAuthenticationToken
                // и передаем первым параметром юзернейм, а вторым пароль
                //таким образом мы перенаправили спринг на нам метод JwtUserDetailsService
                //где спринг будет скать пользователя по юзернейму,иу же из JwtUserDetailsService возвращается спринговый пользователь с помощью
                //JwtEntityFactory из JwtUserDetailsService
                //а потом уже спринг проверит логин и с помощью пассворд энкодера пароль
               // если пароли не совпадут, то будет эксепшн
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(), loginRequest.getPassword())
        );//если авторизация прошла можем идти дальше
// достаем юзера по юзернейму
        User user = userService.getByUsername(loginRequest.getUsername());
        // теперь, когда получили юзера вставляем все данные в параметры в jwtResponse
        jwtResponse.setId(user.getId());
        jwtResponse.setUsername(user.getUsername());
        jwtResponse.setAccessToken(jwtTokenProvider.createAccessToken(
                user.getId(), user.getUsername(), user.getRoles())
        );
        jwtResponse.setRefreshToken(jwtTokenProvider.createRefreshToken(
                user.getId(), user.getUsername())
        );

        return jwtResponse;
    }

    @Override
    public JwtResponse refresh(
            final String refreshToken
    ) {
        return jwtTokenProvider.refreshUserTokens(refreshToken);
    }

}