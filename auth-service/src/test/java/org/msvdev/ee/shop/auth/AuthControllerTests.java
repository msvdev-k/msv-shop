package org.msvdev.ee.shop.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.msvdev.ee.shop.api.jwt.JwtRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTests {

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper;


    @BeforeEach
    public void init() {
        objectMapper = new ObjectMapper();
    }


    @Test
    public void createUserToken() throws Exception {

        String json = objectMapper.writeValueAsString(new JwtRequest("admin", "admin"));

        ResultActions perform = mvc.perform(post("/auth/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json));

        perform.andDo(print());
        perform.andExpect(status().isOk());
        perform.andExpect(jsonPath("$.token").hasJsonPath());
        perform.andExpect(jsonPath("$.token").isNotEmpty());
        perform.andExpect(jsonPath("$.token").isString());
    }
}