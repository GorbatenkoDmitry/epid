package com.epid.epid.repository;

import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;

import java.util.Optional;

public interface UserRepository {

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    void update (User user);

    void create (User user);

    void insertUserRole (Long id, Role role);

    void delete (Long id);


}
