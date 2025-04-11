package com.epid.epid.web.security.expression;

import com.epid.epid.domain.user.Role;
import com.epid.epid.service.UserService;
import com.epid.epid.web.security.JwtEntity;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
//этот класс создан что бы можно было обращаться к данным методом
/// то есть тут мы переопределяем базовый класс SecurityExpressionRoot
//и добовляем в него свои методы
//и мы может пользоваться его классами и своими и писать секьюрити экспрешены
@Setter
@Getter
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot
        implements MethodSecurityExpressionOperations {
// нужно сначала создать эти объекты для работы экспрешн рута

    private Object filterObject;
    private Object returnObject;
    private Object target;
    private HttpServletRequest request;

    private UserService userService;
// необхожимо создать конструктор, который принимает аунтификэйшн  вызывает суперконструктор для него
//Super служит для вызова конструктор базового(родительсокго) класса в конструкторе
 //       класса-потомка. Базовый класс еще называют “суперклассом”,
//    поэтому в Java для его обозначения используется ключевое слово super.
    public CustomMethodSecurityExpressionRoot(
            final Authentication authentication
    ) {
        super(authentication);
    }

    public boolean canAccessUser(
            final Long id
    ) {
        JwtEntity user = (JwtEntity) this.getPrincipal();
        Long userId = user.getId();

        return userId.equals(id) || hasAnyRole(Role.ROLE_ADMIN);
    }

    private boolean hasAnyRole(
            final Role... roles
    ) {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        for (Role role : roles) {
            SimpleGrantedAuthority authority
                    = new SimpleGrantedAuthority(role.name());
            if (authentication.getAuthorities().contains(authority)) {
                return true;
            }
        }
        return false;
    }



    @Override
    public Object getThis() {
        return target;
    }

}