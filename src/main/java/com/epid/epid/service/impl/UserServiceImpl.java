package com.epid.epid.service.impl;

import com.epid.epid.domain.exception.ResourceNotFoundException;
import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;
import com.epid.epid.repository.UserRepository;
import com.epid.epid.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Set;

@Service
@RequiredArgsConstructor
    
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
   // private final MailService mailService;

    @Override
    @Transactional(readOnly = true)
    @Cachable(value = "UserService::getById", key = '#id'")
    public User getById(
            final Long id
    ) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }
    @Override
    @Transactional(readOnly = true)
    @Cachable(value = "UserService::getByUsername", key = '#username'")
    public User getByUsername(
            final String username
    ) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
    @Caching(put = {   
        @Cacheput(value = "UserService::getById", key = '#id'")
        @Cacheput(value = "UserService::getByUsername", key = '#username'")
                   })
    public User update(
            final User user
    ) {
        User existing = getById(user.getId());
        existing.setName(user.getName());
        user.setUsername(user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    @Transactional(readOnly = true)
      @Caching(Cachable = {   
        @Cachable(value = "UserService::getById", key = '#id'")
        @Cachable(value = "UserService::getByUsername", key = '#username'")
                   })
    public User create(
            final User user
    ) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException(
                    "Password and password confirmation do not match."
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = Set.of(Role.ROLE_USER);
        user.setRoles(roles);
     //   userRepository.save(user);
    //    mailService.sendEmail(user, MailType.REGISTRATION, new Properties());
        return user;
    }

      @Override
      @Transactional(readOnly = true)
    @CacheEvict(value = "UserService::getById", key = '#id'")
   public void delete(Long id) {
        try {
            Connection connection = dataSourceConfig.getConnection();
            PreparedStatement statement = connection.prepareStatement(DELETE);
            statement.setLong(1, id);
            statement.executeUpdate();
        } catch (SQLException throwables) {
            throw new ResourceMappingException("Error while DELETING WORKER.");
        }
    }
}
