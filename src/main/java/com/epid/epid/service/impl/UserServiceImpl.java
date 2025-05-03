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
//    @Cachable(value = "UserService::getById", key = "#id")
    public User getById(final Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
  //  @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional
//    @Caching(put = {
 //           @CachePut(value = "UserService::getById", key = "#user.id"),
  //          @CachePut(value = "UserService::getByUsername", key = "#user.username")
  ///  })
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.update(user);
        return user;
    }

    @Override
    @Transactional
 //   @Caching(cacheable = {
  //          @Cacheable(value = "UserService::getById", key = "#user.id"),
  //          @Cacheable(value = "UserService::getByUsername", key = "#user.username")
 //   })
    public User create(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalStateException("User already exists.");
        }
        if (!user.getPassword().equals(user.getPasswordConfirmation())) {
            throw new IllegalStateException("Password and password confirmation do not match.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.create(user);
        Set<Role> roles = Set.of(Role.ROLE_USER);
        userRepository.insertUserRole(user.getId(), Role.ROLE_USER);
        user.setRoles(roles);
        return user;
    }
    @Override
    @Transactional
  //  @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        userRepository.delete(id);
    }
}
