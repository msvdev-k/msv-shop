package org.msvdev.ee.shop.auth;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.msvdev.ee.shop.api.jwt.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.json.JsonContent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;


@JsonTest
public class JsonTests {

    @Autowired
    private JacksonTester<JwtRequest> jacksonTester;



    public static Stream<Arguments> jwtRequestTestData() {
        List<Arguments> out = new ArrayList<>();

        out.add(Arguments.of("User1", "jkhbyt*T^&$DCFU&&", "{\"username\":\"User1\", \"password\": \"jkhbyt*T^&$DCFU&&\"}"));
        out.add(Arguments.of("uSer2", "dksjhd8^R^%Fuyg"  , "{\"username\":\"uSer2\", \"password\": \"dksjhd8^R^%Fuyg\"}"));
        out.add(Arguments.of("usEr3", "dsaf*^&v7vg78"    , "{\"username\":\"usEr3\", \"password\": \"dsaf*^&v7vg78\"}"));
        out.add(Arguments.of("useR4", "sadifhas787t6g"   , "{\"username\":\"useR4\", \"password\": \"sadifhas787t6g\"}"));

        return out.stream();
    }


    @ParameterizedTest
    @MethodSource("jwtRequestTestData")
    public void jwtRequestSerializationTest(String username, String password, String content) throws IOException {

        JwtRequest request = new JwtRequest(username, password);

        JsonContent<JwtRequest> jsonContent = jacksonTester.write(request);

        assertThat(jsonContent)
                .hasJsonPathStringValue("$.username")
                .hasJsonPathStringValue("$.password");

        assertThat(jsonContent)
                .extractingJsonPathValue("$.username")
                .isEqualTo(username);

        assertThat(jsonContent)
                .extractingJsonPathValue("$.password")
                .isEqualTo(password);
    }


    @ParameterizedTest
    @MethodSource("jwtRequestTestData")
    public void jwtRequestDeserializationTest(String username, String password, String content) throws IOException {

        JwtRequest request = new JwtRequest(username, password);
        JwtRequest parseObject = jacksonTester.parseObject(content);

        assertThat(parseObject.getUsername())
                .isEqualTo(username);

        assertThat(parseObject.getUsername())
                .isEqualTo(username);

    }
}