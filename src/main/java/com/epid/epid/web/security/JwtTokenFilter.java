package com.epid.epid.web.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
//класс который фильтрует наши токены jwt java web token
//он экстрендит GenericFilterBean это один из бинов спринга, который позволяет реали
//зовывать базовую фильтрацию
/// как всегда вешаем  AllArgsConstructor, что бы переменные в объектах инциализировались через контрукторы

@AllArgsConstructor
public class JwtTokenFilter extends GenericFilterBean {
//помещаем сюда JwtTokenProvider
    private final JwtTokenProvider jwtTokenProvider;
//так же имплементим фильтр из GenericFilterBean и переопределяем его
    @Override
    @SneakyThrows
    public void doFilter(
            final ServletRequest servletRequest,
            final ServletResponse servletResponse,
            final FilterChain filterChain
    ) { // мы делаем фильтр, который до авторизации принимает токен и досатает из хедера
        ///сначла помещаем токен в хидер
        //мы через servletRequest это объект методода servlet's service, которыйй передает параметры в реквест запрос
        // однако нужно еще скастить(привести) все  к http и для это пользуемся HttpServletRequest
//Интерфейс HttpServletRequest
//При каждом вызове методы doGet и doPost класса HttpServlet принимают в качестве параметра объект,
// который реализует интерфейс HttpServletRequest.
        String bearerToken = ((HttpServletRequest) servletRequest)
                .getHeader("Authorization");
        //если токен не равен null и начинается с Bearer
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            //то мы этот токен берем и с 7 позиции обрезаем его ( то есть удаляем само словао Bearer и пробел
            bearerToken = bearerToken.substring(7);
        }
        //далее если в фильтре токен будет не валиидный, то будет выбрасываться ошибка
        try {
            //далее если токен не нулевой
            if (bearerToken != null
                    // и токен проходит проверку валидности
                    && jwtTokenProvider.isValid(bearerToken)) {
                //мы пробуем провести и получить  аунтификаицю( это все класс спрингсекьюрити)
                Authentication authentication
                        = jwtTokenProvider.getAuthentication(bearerToken);
                // и если аунтификаиция не равна нулю
                if (authentication != null) {
                    //тогда мы спрингу сообщаем, что мы авторизоваля пользователя.
                    //делаем это через вызов getContext и помщеения в это поле authentication
                    SecurityContextHolder.getContext()
                            .setAuthentication(authentication);
                }
            }
            // и тут кидаетс эксепшн если что то не так и мы ничего не будем делать дальше
            //поэтому пишем игнорид
        } catch (Exception ignored) {
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}