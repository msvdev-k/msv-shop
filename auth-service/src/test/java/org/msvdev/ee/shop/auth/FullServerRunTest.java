package org.msvdev.ee.shop.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.msvdev.ee.shop.api.jwt.JwtRequest;
import org.msvdev.ee.shop.api.jwt.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullServerRunTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private ObjectMapper objectMapper;
    private HttpHeaders headers;


    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();

        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }


    @Test
    public void createUserTokenTest() throws JsonProcessingException {

        String json = objectMapper.writeValueAsString(new JwtRequest("admin", "admin"));

        HttpEntity<String> request = new HttpEntity<>(json, headers);

        JwtResponse response = restTemplate
                .postForObject("/auth/", request, JwtResponse.class);

        assertThat(response)
                .isNotNull();
    }
}