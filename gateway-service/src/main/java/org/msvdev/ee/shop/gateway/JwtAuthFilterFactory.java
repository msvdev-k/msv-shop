package org.msvdev.ee.shop.gateway;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;


@Component
public class JwtAuthFilterFactory extends AbstractGatewayFilterFactory<JwtAuthFilterFactory.Config> {

    static private final String AUTHORIZATION = "Authorization";
    static private final String BEARER = "Bearer ";
    static private final String USERNAME = "username";
    static private final String ROLES = "roles";


    @Autowired
    private JwtTokenParser jwtTokenParser;


    public JwtAuthFilterFactory() {
        super(Config.class);
    }


    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {

            ServerHttpRequest request = exchange.getRequest();
            HttpHeaders headers = request.getHeaders();

            if (headers.containsKey(USERNAME) || headers.containsKey(ROLES)) {
                ServerHttpResponse response = exchange.getResponse();
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                return response.setComplete();
            }

            if (isAuthPresent(headers)) {
                final String token = headers
                        .getOrEmpty(AUTHORIZATION)
                        .get(0)
                        .substring(7);

                if (jwtTokenParser.isInvalid(token)) {
                    ServerHttpResponse response = exchange.getResponse();
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    return response.setComplete();
                }

                Claims claims = jwtTokenParser.getAllClaimsFromToken(token);
                exchange.getRequest().mutate()
                        .header(USERNAME, claims.getSubject())
                        .header(ROLES, jwtTokenParser.getRoles(token).toArray(new String[0]))
                        .build();
            }

            return chain.filter(exchange);
        };
    }


    /**
     * Проверка заголовка на наличие токена авторизации
     *
     * @param headers заголовки запроса
     * @return true - в заголовке запроса присутствует токен авторизации;
     * false - токена в заголовке нет
     */
    private boolean isAuthPresent(HttpHeaders headers) {
        return headers.containsKey(AUTHORIZATION) &&
               headers.getOrEmpty(AUTHORIZATION).get(0).startsWith(BEARER);
    }


    public static class Config {}
}