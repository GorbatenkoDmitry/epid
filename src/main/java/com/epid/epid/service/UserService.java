package com.epid.epid.service;

import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;

import java.util.Optional;

public interface UserService {

    User getById(Long id);

    User getByUsername(String username);

    User update(User user);

    User create(User user);


    void delete(Long id);

}