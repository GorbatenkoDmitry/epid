package com.epid.epid.web.dto.user;

import com.epid.epid.domain.user.Role;
import com.epid.epid.web.dto.validation.OnCreate;
import com.epid.epid.web.dto.validation.OnUpdate;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.util.Set;
@Data

public class UserDto {

    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @NotNull(message = "Name must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String name;

    @NotNull(message = "Username must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String username;

    //Аннотация не дает через дто передавать данные о пароле фронту из БД
    //поэтому только принимает("читает") json  с фронта
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnUpdate.class,OnCreate.class})
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnCreate.class})
    private String passwordConfirmation;
 }
