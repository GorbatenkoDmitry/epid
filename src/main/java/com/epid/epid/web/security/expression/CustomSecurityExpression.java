package com.example.epid.web.security.expression;

import com.epid.epid.domain.user.Role;
import com.epid.epid.service.UserService;
import com.epid.epid.web.security.JwtEntity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
//создаем новый бин 
@Component("CustomSecurityExpression")
//  делает заполнение конструктора, потому что будем инжектить юзерсервис
@RequiredArgsConstructor
public class CustomSecurityExpression {
//создали переменную юзерсервис
    private final UserService userService;
//функция предоставляющая доступ
    public boolean canAccessUser(
            final Long id
    ) {
      // создаем объект юзер, метод который нужен Чтобы получить информацию о пользователе из Authentication
        JwtEntity user = getPrincipal();
      // получаем у этого бзера айди
        Long userId = user.getId();
     //сранвиваем полученный аиди из аундификации и переданным в метод айди,  или (две палки)  метод hasAnyRole который сравнивает роль аунтифицированного пользователя с заданной ( у нас Role.ROLE_ADMIN)
        return userId.equals(id) || hasAnyRole(Role.ROLE_ADMIN);
    }
// hasAnyRole метод контекст холдер с помощью метода getContext и метода getAuthentication достает инфу, которая содержит роли, а затем в цикле их перебираем
    private boolean hasAnyRole(
            final Role... roles
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
              for (Role role : roles) {
        //   и записываем в authority объект SimpleGrantedAuthority с именем роли
            SimpleGrantedAuthority authority
                    = new SimpleGrantedAuthority(role.name());
          //а затем передаем методу contains authority
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }


    private JwtEntity getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return (JwtEntity) authentication.getPrincipal();
    }

}
