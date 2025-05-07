package com.epid.epid.web.dto.auth;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
//Данный класс будет проверять введенные данные с фронта с контроллера и затем будет возвращаться в JwtResponse
@Data
    @Schema(description = "Request for login")

public class JwtRequest {
    @Schema(description = "email",example = "johndoe@gmail.com")
    @NotNull(message = "Username must be not null.")
    private String username;
    
    @Schema(description = "password",example = "12345")
    @NotNull(message = "Password must be not null.")
    private String password;
}
