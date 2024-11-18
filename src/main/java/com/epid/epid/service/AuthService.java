package com.epid.epid.service;

import com.epid.epid.web.dto.auth.JwtRequest;
import com.epid.epid.web.dto.auth.JwtResponse;

public interface AuthService {
    JwtResponse login (JwtRequest loginRequest);

    JwtResponse refresh (String refreshToken);
}
