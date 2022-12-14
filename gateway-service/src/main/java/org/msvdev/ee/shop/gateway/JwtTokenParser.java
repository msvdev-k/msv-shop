package org.msvdev.ee.shop.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;


@Component
public class JwtTokenParser {

    // Ключ для подписи токена
    private final SecretKey key;


    @Autowired
    public JwtTokenParser(@Value("${jwt.secret}") String secret) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }


    /**
     * Получить из токена имя пользователя
     * @param token строка токена
     * @return имя пользователя
     */
    public String getUsernameFromToken(String token) {
        return getAllClaimsFromToken(token).getSubject();
    }


    /**
     * Получить из токена список ролей
     * @param token строка токена
     * @return список ролей
     */
    public List<String> getRoles(String token) {
        return (List<String>) getAllClaimsFromToken(token).get("roles");
    }


    public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public boolean isInvalid(String token) {
        return getAllClaimsFromToken(token)
                .getExpiration().before(new Date());
    }

}