package com.epid.epid.web.security;

import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
//из названия ясно,что класс создает JwtEntity, то есть предстовления пользователя для спринга
//тут реализуется паттерн фаткори, который заключается в создании из юзера JwtEntity
public final class JwtEntityFactory {
//метод создания и передает юзер
    public static JwtEntity create(
            final User user
    ) {
        //забираем данные из юзера и создаем объект JwtEntity,инициируем его(то есть заполняем поля)
        return new JwtEntity(
                user.getId(),
                user.getUsername(),
                user.getName(),
                user.getPassword(),
                //так как мы все храним в сете, то нужен метод что бы все это мэпить
                mapToGrantedAuthorities(new ArrayList<>(user.getRoles()))
        );
    }
//метод мэппит роли
    private static List<GrantedAuthority> mapToGrantedAuthorities(
            //в метод приниматеся лист в виде наших енамов
            final List<Role> roles
    ) {
        //(переводим) мэппим роли из листа на спринг секьюрити  и тогда спринг будет нас понимать
        //вызываем стандартный метод стреам, с помощью которого мы можем выполнять операции с элементами массива или коллекции

        return roles.stream()
                // тут мэппим енам
                .map(Enum::name)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

}