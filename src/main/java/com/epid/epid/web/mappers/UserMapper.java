package com.epid.epid.web.mappers;

import com.epid.epid.domain.user.User;
import com.epid.epid.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
//Метод переводит "юзера из БД" в ДТО, который потом пойдет в контроллер
    UserDto toDto(User user);

    //Метод переводит UserDto в наш обычный объект юзера
    User toEntity(UserDto dto);
}
