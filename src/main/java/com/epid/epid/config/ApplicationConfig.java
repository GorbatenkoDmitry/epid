package com.epid.epid.config;
//класс отвечает за конфигурацию спринг секьюрити

//пишем, что этот фаил конфигурационный(хотя по сути все эти аннотации сервис,конфигурэйшн делают примерно одно
//  и то же, а именно помещают фаил в аппликатион контекст, то есть делают его видимым спрингу

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
//Далее уже пишем аннотацию,которая включит спринг секьюрити и в ней же будет происходить насттройка
// Spring Security — это фреймворк на основе фильтров. Мы либо включаем существующий фильтр и настраиваем его, либодобавляем собственный фильтр.
@EnableWebSecurity
//и будем вызывать  конструкторы  со всем переменными
@RequiredArgsConstructor
public class ApplicationConfig {

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

// SecurityFilterChain  класс в спринг секьюрити, который создан для настройки спринг секьюрити
// которые могут выключать и включать, то есть настривать например переданный в параметры HttpSecurity
    // помечаем бин ччто бы его поместить в аппликатион контекст
    //далее создаем метод filterChain, который возвращает SecurityFilterChain и передаем в параметры
    //класс HttpSecurity содержит методы защиты спринг секьюрити ,вызываем (включаем нужные ) и выключаем лишние.
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //Сross Site Request Forgery) в переводе на русский — это подделка межсайтовых запросов
                .csrf().disable()
                //один сайт делает запрос другому, а у другого настройки можно ли отправлять данные, например фото
                .cors()
                .and()
                //Spring HttpSecurity httpBasic() Настраивает базовую аутентификацию HTTP.когда пароль в концоли.он нам не нужен
                .httpBasic().disable()
                //Spring HttpSecurity включаем sessionManagement() Позволяет настраивать управление сеансами.
                .sessionManagement()
                // SessionCreationPolicy.STATELESS в Spring Security означает, что приложение не будет
                // использовать HTTP-сессии для хранения информации о безопасности (например, состояния
                // аутентификации пользователя).
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                //включили обработку исключений
                .exceptionHandling()
                //если авторизация не прошла,то выбрасывается исключение, что бы их ловить пишем лямду
                .authenticationEntryPoint((request, response, authException) -> {
                    //в ответ в метод setStatus передаем HttpStatus.UNAUTHORIZED
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    //и пишем ответ
                    response.getWriter().write("Unauthorized.");
                })
//то же самое если доступ был запрещен
                .accessDeniedHandler((request, response, accessDeniedException) -> {
                    response.setStatus(HttpStatus.FORBIDDEN.value());
                    response.getWriter().write("Unauthorized.");
                })
                .and()

                //authorizeHttpRequests метод который позволяет вешать настройки проверки авторизован ли
                //пользователь на это странице
                .authorizeHttpRequests()
                //requestMatchers конечная точка и проверка доступа permitAll-разрешать всем заъходить на эту страницу
                .requestMatchers("/api/v1/auth/**").permitAll()
                .requestMatchers("/swagger-ui/**").permitAll()
                .requestMatchers("/v3/api-docs/**").permitAll()
                //остальные запросы (без настройки) только авторизованным
                .anyRequest().authenticated()
                .and()
                //отключаем возможность анонимного посещения
                .anonymous().disable()
//Позволяет добавлять Filter перед одним из известных Filter классов.
                //передаем два параметра JwtTokenFilter(tokenProvider)- два класса авторизации на токенах
                // и класс перед которым он будет работать
                //UsernamePasswordAuthenticationFilter.class
                .addFilterBefore(new JwtTokenFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }

}
