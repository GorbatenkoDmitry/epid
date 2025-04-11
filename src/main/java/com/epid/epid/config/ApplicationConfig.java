package com.epid.epid.config;

import com.epid.epid.web.security.JwtTokenFilter;
import com.epid.epid.web.security.JwtTokenProvider;
import com.epid.epid.web.security.expression.CustomSecurityExceptionHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//класс отвечает за конфигурацию спринг секьюрити

//пишем, что этот фаил конфигурационный(хотя по сути все эти аннотации сервис,конфигурэйшн делают примерно одно
//  и то же, а именно помещают фаил в аппликатион контекст, то есть делают его видимым спрингу

@Configuration
//Далее уже пишем аннотацию,которая включит спринг секьюрити и в ней же будет происходить насттройка
// Spring Security — это фреймворк на основе фильтров. Мы либо включаем существующий фильтр и настраиваем его, либодобавляем собственный фильтр.
@EnableWebSecurity
//
@EnableGlobalMethodSecurity(prePostEnabled = true)//и будем вызывать  конструкторы  со всем переменными

@RequiredArgsConstructor
public class ApplicationConfig {
    private final JwtTokenProvider tokenProvider;

    //инжектим то есть вызываем в переменной класс ApplicationContext,который отвечает за создание и хранение бинов
    //в нем есть все созданные бины

private final ApplicationContext applicationContext;

//далее помечаем бином пассворд енкодер что бы его видел спринг,потому что это не класс, а спринг секьюрити/
    //Этот класс у нас будет хэшировать наши пароли, когда мы будем сохранять пароли
    @Bean
public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//Вызываем AuthenticationManager который из спринг секьюрити и выполняет функцию аунтификацию(проверки данных юзера)
// и передается в параметры переменную конфигурации
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
return configuration.getAuthenticationManager();
    }


    @Bean
    public MethodSecurityExpressionHandler expressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler
                = new CustomSecurityExceptionHandler();
        expressionHandler.setApplicationContext(applicationContext);
        return expressionHandler;
    }
// SecurityFilterChain  класс в спринг секьюрити, который создан для настройки спринг секьюрити
// которые могут выключать и включать, то есть настривать например переданный в параметры HttpSecurity
    // помечаем бин ччто бы его поместить в аппликатион контекст
    //далее создаем метод filterChain, который возвращает SecurityFilterChain и передаем в параметры
    //класс HttpSecurity содержит методы защиты спринг секьюрити ,вызываем (включаем нужные ) и выключаем лишние.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //Сross Site Request Forgery) в переводе на русский — это подделка межсайтовых запросов
                .csrf(AbstractHttpConfigurer::disable)
                //один сайт делает запрос другому, а у другого настройки можно ли отправлять данные, например фото
                .cors(AbstractHttpConfigurer::disable)
                //Spring HttpSecurity httpBasic() Настраивает базовую аутентификацию HTTP.когда пароль в концоли.он нам не нужен
                .httpBasic(AbstractHttpConfigurer::disable)
                //Spring HttpSecurity включаем sessionManagement() Позволяет настраивать управление сеансами.
                .sessionManagement(sessionManagement ->
                        sessionManagement
                        // SessionCreationPolicy.STATELESS в Spring Security означает, что приложение не будет
                        // использовать HTTP-сессии для хранения информации о безопасности (например, состояния
                        // аутентификации пользователя).
                        .sessionCreationPolicy(
                                        SessionCreationPolicy.STATELESS
                                )
                )
                //включили обработку исключений
                .exceptionHandling(configurer ->
                        //если авторизация не прошла,то выбрасывается исключение, что бы их ловить пишем лямду
                        configurer.authenticationEntryPoint(
                                (request, response, exception) -> {
//в ответ в метод setStatus передаем HttpStatus.UNAUTHORIZED
                                    response.setStatus(
                                            HttpStatus.UNAUTHORIZED
                                                    .value()
                                    );
                    //и пишем ответ
                    response.getWriter().write("Unauthorized.");
                })
//то же самое если доступ был запрещен
                                .accessDeniedHandler(
                                        (request, response, exception) -> {
                                            response.setStatus(
                                                    HttpStatus.FORBIDDEN
                                                            .value()
                                            );
                                            response.getWriter()
                                                    .write("Unauthorized.");
                                        }))

                //authorizeHttpRequests метод который позволяет вешать настройки проверки авторизован ли
                //пользователь на это странице
                .authorizeHttpRequests(configurer ->

    //requestMatchers конечная точка и проверка доступа permitAll-разрешать всем заъходить на эту страницу
                configurer.requestMatchers("/api/v1/auth/**")
                .permitAll()
                .requestMatchers("/swagger-ui/**")
                .permitAll()
                .requestMatchers("/v3/api-docs/**")
                .permitAll()
                //остальные запросы (без настройки) только авторизованным
                .anyRequest().authenticated())

                //отключаем возможность анонимного посещения
                 .anonymous(AbstractHttpConfigurer::disable)
//Позволяет добавлять Filter перед одним из известных Filter классов.
                //передаем два параметра JwtTokenFilter(tokenProvider)- два класса авторизации на токенах
                // и класс перед которым он будет работать
                //UsernamePasswordAuthenticationFilter.class
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
