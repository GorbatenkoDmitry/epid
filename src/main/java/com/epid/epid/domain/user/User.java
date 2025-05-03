package com.epid.epid.domain.user;

import lombok.Data;
import java.io.Serializable;
import java.util.Set;

@Data
public class User implements Serializable {
    private Long id;
    private String name;
    private String Surname;
    private String username;
    private String password;
    private String passwordConfirmation;
    private Set<Role> roles;
}
