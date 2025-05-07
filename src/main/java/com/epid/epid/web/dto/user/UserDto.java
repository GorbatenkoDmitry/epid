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
    //тег схема нам определяет структуру данных в которой будет отображаться ответ или запрос
@Schema(description = "User DTO")
public class UserDto {
    //Делаем вручную примере данных, которые будут отображаться
@Schema(description = "User id",example = "1")
    @NotNull(message = "Id must be not null.", groups = OnUpdate.class)
    private Long id;

    @Schema(description = "User name",example = "John Doe")
    @NotNull(message = "Name must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String name;

    @Schema(description = "User surname",example = "Doe")
    @NotNull(message = "Username must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String surname;

    @Schema(description = "User email",example = "johndoe@gmail.com")
    @NotNull(message = "Username must be not", groups = {OnCreate.class,OnUpdate.class})
    @Length(max = 255, message = "Length must be smaller,than 255 symbols")
    private String username;

    //Аннотация не дает через дто передавать данные о пароле фронту из БД
    //поэтому только принимает("читает") json  с фронта
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnUpdate.class,OnCreate.class})
    @Schema(description = "User password",example = "$2a$12$.INfqW6bPZwgREP6EMsSaOzXbaij0z2htp5Evd2q0d6VwTaS3QuJi")
    private String password;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull(message = "Password must be not null", groups = {OnCreate.class})
    private String passwordConfirmation;
 }
