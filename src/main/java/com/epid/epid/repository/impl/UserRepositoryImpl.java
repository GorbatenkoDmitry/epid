package com.epid.epid.repository.impl;

import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;
import com.epid.epid.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final String FIND_BY_ID """
SELECT u.id as user_id,
    u.name as user_name,
    u.username as user_username,
    u.password as user_password,
    ur.role as user_role,
    FROM users u
    LEFT JOIN users_role ur on u.id = ur.user_id
    WHERE u.id = ?
    """;
    
   private final String FIND_BY_USERNAME """
SELECT u.id as user_id,
    u.name as user_name,
    u.username as user_username,
    u.password as user_password,
    ur.role as user_role,
    FROM users u
    LEFT JOIN users_role ur on u.id = ur.user_id
    WHERE u.username = ?
    """;


  private final String DELETE = """
            DELETE FROM users
            WHERE id = ?""";

    private final String CREATE = """
            INSERT INTO users (name,username,password)
            VALUES (?,?,?)""";

    private final String UPDATE = """
            UPDATE users
            SET name = ?,
            username = ?,
            password = ?
            WHERE id = ?""";

    private final String INSERT_USER_STATUS = """
          INSERT INTO users_roles (users_id,role)
            VALUES (?,?)""";


    @Override
    public Optional<User> findById(Long id) {

    
    
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void update(User user) {

    }

    @Override
    public void create(User user) {

    }

    @Override
    public void insertUserRole(Long id, Role role) {

    }

    @Override
    public void delete(Long id) {

    }
}
