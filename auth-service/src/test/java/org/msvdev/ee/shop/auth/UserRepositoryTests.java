package org.msvdev.ee.shop.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.msvdev.ee.shop.auth.entity.User;
import org.msvdev.ee.shop.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;


@DataJpaTest
@ActiveProfiles("test")
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;


    @MethodSource
    @ParameterizedTest
    public void findByUsernameTest(String username, boolean isPresent) {
        Optional<User> user = userRepository.findByUsername(username);

        Assertions.assertTrue(isPresent ? user.isPresent() : user.isEmpty());

        if (isPresent) {
            Assertions.assertTrue(username.equalsIgnoreCase(user.get().getUsername()));
        }
    }


    public static Stream<Arguments> findByUsernameTest() {
        List<Arguments> out = new ArrayList<>();

        out.add(Arguments.of("admin", true));
        out.add(Arguments.of("user", true));
        out.add(Arguments.of("user2", false));
        out.add(Arguments.of("user3", false));

        return out.stream();
    }
}