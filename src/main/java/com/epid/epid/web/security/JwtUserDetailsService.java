package com.epid.epid.web.security;
//это сервис будет вызываться спрингом для авторизации пользователей
import com.epid.epid.domain.user.User;
import com.epid.epid.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
//спринг вызывает данный метод через UserDetailsService при авторизации пользователя,  потому что мы унаследовалиьс у
// UserDetailsService
@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserService userService;
//переопределяем один метод
    @Override
    public UserDetails loadUserByUsername(
            final String username
    ) {
        //достали через сервис из бд юзера и передали его JwtEntityFactory
        User user = userService.getByUsername(username);
        return JwtEntityFactory.create(user);
    }

}