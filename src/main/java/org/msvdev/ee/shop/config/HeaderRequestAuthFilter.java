package org.msvdev.ee.shop.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class HeaderRequestAuthFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String username = request.getHeader("username");

        List<String> roles = new ArrayList<>();
        request.getHeaders("roles").asIterator().forEachRemaining(roles::add);

        SecurityContext securityContext = SecurityContextHolder.getContext();


        if (username != null && !roles.isEmpty() && securityContext.getAuthentication() == null) {

            List<SimpleGrantedAuthority> grantedAuthorityList =
                    roles.stream()
                            .map(SimpleGrantedAuthority::new)
                            .toList();

            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username,
                            null,
                            grantedAuthorityList);

            securityContext.setAuthentication(token);
        }

        filterChain.doFilter(request, response);
    }
}
