package com.epid.epid.service.impl;

import com.epid.epid.service.AuthService;
import com.epid.epid.web.dto.auth.JwtRequest;
import com.epid.epid.web.dto.auth.JwtResponse;

public class AuthServiceImpl implements AuthService {
    @Override
    public JwtResponse login(JwtRequest loginRequest) {
        return null;
    }

    @Override
    public JwtResponse refresh(String refreshToken) {
        return null;
    }
}
