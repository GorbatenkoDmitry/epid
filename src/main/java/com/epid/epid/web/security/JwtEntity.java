package com.epid.epid.web.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Getter
@Setter
@AllArgsConstructor
///спрингу нужны данные о пользователе в определеенном формате (формат класса юзер детеил)
//имплементируем его и все методы наследуем, но реализовывать их пока не будем
public class JwtEntity implements UserDetails {
//в этих получаем данные пользователя, через конструктор @AllArgsConstructor.
    //при это так как они файнал,они будут инжектиться
    private final Long id;
    private final String username;
    private final String name;
    private final String password;
    //тут хранится коллекция ролей полномочий,который имеет пользователь для спринга
    private final Collection<? extends GrantedAuthority> authorities;
//далее мы не будем переопределять методы,а просто возратим данные или тру и передадим данные на другой уровень
//при чем по идее наслдуются еще getUsername, getPassword,getAuthorities , но так как у нас есть аннотация геттер
    //сеттер и переменные  username; password;authorities, то у нас  они автоматически созданы и просто скрыты
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}