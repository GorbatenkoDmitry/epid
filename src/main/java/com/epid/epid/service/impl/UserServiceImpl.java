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

    public User getById(
            final Long id
    ) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }
    @Override
    @Transactional(readOnly = true)
    public User getByUsername(
            final String username
    ) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found."));
    }

    @Override
    @Transactional(readOnly = true)
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

      public void delete(Long id) {

    }
}
