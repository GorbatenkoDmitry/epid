package com.epid.epid.web.security;

import com.epid.epid.domain.exception.AccessDeniedException;
import com.epid.epid.domain.user.Role;
import com.epid.epid.domain.user.User;
import com.epid.epid.service.UserService;
import com.epid.epid.service.props.JwtProperties;
import com.epid.epid.web.dto.auth.JwtResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private SecretKey key;
// @PostConstruct чтоы бы private final заавтовайрились раньше,чем метод init.
    //в методе мы создаем ключ
    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes());
    }

    public String createAccessToken(
            final Long userId,
            final String username,
            final Set<Role> roles
    ) {//Объекты claim представляют некоторую информацию о пользователе в веб токене,
        // которую мы можем использовать для авторизации в приложении
        // jwts это jsonwebtokeb и вызываем клаймс

        Claims claims = Jwts.claims()
                //Claim.Subject Свойство (Субъект утверждения).
                //Субъектом является сущность, о которой утверждается утверждение.
                //Мы пишем username  и у нас в токене будет идти сначала юзернецм
                .subject(username)
                //add работает как маппа и переводит наше поле юзерайди в поле для jwts
                //сначала я так понял мы пишем название нашего поля и потом значение переменноц
                .add("id", userId)
                .add("roles", resolveRoles(roles))
                .build();
        //Instant класс отражает время в настоящий момент
        Instant validity = Instant.now()
// я так понимаю этот метод прибавляет текущее время и jwtProperties.getAccess(время жизни токена)
                //а второе это указываме,что время жизни считать в часах
                .plus(jwtProperties.getAccess(), ChronoUnit.HOURS);
        // возвращаем стринг
        //Чтобы сгенерировать JWT, мы можем использовать метод Jwts.builder() и
        // объединить некоторые из его методов, чтобы задать требования, срок действия и подписать токен:
        return Jwts.builder()
                .claims(claims)
                //истечение срока действия
                .expiration(Date.from(validity))
                //подписываем токен с помощью ключа
                .signWith(key)
                //и это все собираем
                .compact();
    }

    private List<String> resolveRoles(
            final Set<Role> roles
    ) {
        return roles.stream()
                //Операция map используется для применения преобразования к каждому элементу в потоке, что приводит к однозначному сопоставлению (one-to-one mapping). Она создает новый поток, в котором каждый входной элемент преобразуется в соответствующий выходной элемент.
               //Когда нужно использовать map:
                //Если вы хотите преобразовать каждый элемент потока в отдельный объект.
                //Если между входными и выходными элементами существует однозначное сопоставление “один к одному”.
                //Вот аналогия из реальной жизни, которая поможет понять  между flatMap и map .
                // Представьте, что у вас есть список людей,
                // и у каждого человека есть список своих любимых цветов.
                // Вы хотите извлечь из списка людей все их любимые цвета.
                // Используя Stream API, вы с помощью map можете извлечь список любимых цветов каждого
                // человека, а с помощью flatMap можете извлечь все цвета из всех списков
                // без привязки к конкретному человеку
                //тут такими людьми являются сет ролей и из сета ролей мы получили стринги
                .map(Enum::name)
                .collect(Collectors.toList());
    }
//рефреш токен просто обновляет пару токенов и не здесь не требуются роли
    public String createRefreshToken(
            final Long userId,
            final String username
    ) {
        Claims claims = Jwts.claims()
                .subject(username)
                .add("id", userId)
                .build();
        Instant validity = Instant.now()
                //тут  уже в отличии от эксесс в днях пишем время работы токена
                .plus(jwtProperties.getRefresh(), ChronoUnit.DAYS);
        return Jwts.builder()
                .claims(claims)
                .expiration(Date.from(validity))
                //
                .signWith(key)
                .compact();
    }

    //метод возврающий тип JwtResponse, который состоит из айди юзернейма и пары токеенов
    public JwtResponse refreshUserTokens(
            final String refreshToken
    ) {
        JwtResponse jwtResponse = new JwtResponse();
        // тут делаем проверку и если выполнив проверку через isValid - не валидный, то выбрасываем исключение
        if (!isValid(refreshToken)) {
            throw new AccessDeniedException();
        }
        //если все хорошо, достает юзера
        //берем айли
        //гет айди парсит рефреш токен и заюирает айди
        Long userId = Long.valueOf(getId(refreshToken));
        //затем этот айди передаем в юзерсервис в метод гетбай айди
        User user = userService.getById(userId);
        //теперь инициализируем поле айди
        jwtResponse.setId(userId);
        // теперь инициализируем поле ЮзерНейм
        jwtResponse.setUsername(user.getUsername());
        //Теперь инициализируем Аксесс токер через метод создать аксесс токен
        jwtResponse.setAccessToken(
                createAccessToken(userId, user.getUsername(), user.getRoles())
        );

        //то же самое с рефреш
        jwtResponse.setRefreshToken(
                createRefreshToken(userId, user.getUsername())
        );
        return jwtResponse;
    }



    public boolean isValid(
            final String token
    ) {
        Jws<Claims> claims = Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
        return claims.getPayload()
                .getExpiration()
                .after(new Date());
    }

    private String getId(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("id", String.class);
    }

    private String getUsername(
            final String token
    ) {
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
///метод предостовляет спрингу информацию о проверенном пользователе . в методе
// (UsernamePasswordAuthenticationToken) мы ему передаем информацию о пользователе авторизованном
    public Authentication getAuthentication(
            final String token
    ) {
        String username = getUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(
                username
        );
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }

}