package org.msvdev.ee.shop.auth.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.msvdev.ee.shop.auth.entity.User;
import org.msvdev.ee.shop.auth.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Получить пользователя по его имени
     * @param username имя пользователя
     * @return Optional<User>
     */
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority(role.getName()))
                                .toList()
                ))
                .orElseThrow(() -> new UsernameNotFoundException(String.format("Пользователь '%s' не найден", username)));
    }
}