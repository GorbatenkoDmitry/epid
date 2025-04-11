package com.epid.epid.web.security.expression;

import com.epid.epid.service.UserService;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.authentication.AuthenticationTrustResolverImpl;
import org.springframework.security.core.Authentication;
/// мы переопределели секьюрити экспрешн рут, но мы переопределяем и хэндлер
public class CustomSecurityExceptionHandler
        extends DefaultMethodSecurityExpressionHandler {

    private ApplicationContext applicationContext;
    private final AuthenticationTrustResolver trustResolver
            = new AuthenticationTrustResolverImpl();

    @Override
    protected MethodSecurityExpressionOperations createSecurityExpressionRoot(
            final Authentication authentication,
            final MethodInvocation invocation
    ) {
        ///создали объект нашего рута
        CustomMethodSecurityExpressionRoot root
                = new CustomMethodSecurityExpressionRoot(authentication);
        ///и передаем руту траст резолвер
        root.setTrustResolver(trustResolver);
        // объект, который будет обрабатывать наши выраженияи нам не нужно задумывать
        // и создавать классы обработчики
        root.setPermissionEvaluator(getPermissionEvaluator());
        root.setRoleHierarchy(getRoleHierarchy());
        root.setUserService(this.applicationContext.getBean(UserService.class));
        return root;
    }

    @Override
    public void setApplicationContext(
            final ApplicationContext applicationContext
    ) {
        super.setApplicationContext(applicationContext);
        this.applicationContext = applicationContext;
    }
}