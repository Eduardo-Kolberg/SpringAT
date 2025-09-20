package com.infnet.at;

import static org.junit.jupiter.api.Assertions.*;

import com.infnet.at.config.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtUtilTest {

    private JwtUtil jwtUtil;

    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();


        String secret = "mysecretkeymysecretkeymysecretkey"; // 32+ chars
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", secret);
    }

    @Test
    void testGenerateTokenAndValidate() {
        String username = "admin";

        String token = jwtUtil.generateToken(username);

        assertNotNull(token, "Token não deve ser nulo");
        assertTrue(jwtUtil.validateToken(token), "Token deve ser válido");

        String extractedUsername = jwtUtil.getUsernameFromToken(token);
        assertEquals(username, extractedUsername, "Username extraído deve ser igual ao original");
    }

    @Test
    void testValidateToken_InvalidToken() {
        String invalidToken = "token-invalido";

        assertFalse(jwtUtil.validateToken(invalidToken), "Token inválido deve retornar false");
    }
}
