package com.epid.epid.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
//Данный класс будет проверять введенные данные с фронта с контроллера и затем будет возвращаться в JwtResponse
@Data
public class JwtRequest {
    @NotNull(message = "Username must be not null.")
    private String username;
    @NotNull(message = "Password must be not null.")
    private String password;
}
