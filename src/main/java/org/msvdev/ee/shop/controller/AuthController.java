package org.msvdev.ee.shop.controller;

import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.dto.JwtRequest;
import org.msvdev.ee.shop.dto.JwtResponse;
import org.msvdev.ee.shop.service.UserService;
import org.msvdev.ee.shop.utils.JwtTokenUtil;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationProvider authenticationProvider;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public JwtResponse createUserToken(@RequestBody JwtRequest jwtRequest) {

        authenticationProvider.authenticate(
                new UsernamePasswordAuthenticationToken(
                        jwtRequest.getUsername(),
                        jwtRequest.getPassword()
                )
        );

        UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        return new JwtResponse(token);
    }
}
