package BankApp.SpringBank.service;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Collections;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService testService;

    @Mock
    private UserDetails userDetails;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        // ✅ Инжектируем @Value поля через ReflectionTestUtils
        ReflectionTestUtils.setField(jwtService, "secretKey",
                "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970");
        ReflectionTestUtils.setField(jwtService, "accessTokenExpiration", 900000L);
        ReflectionTestUtils.setField(jwtService, "refreshTokenExpiration", 604800000L);

        userDetails = new User(
                "testuser",
                "password",
                Collections.emptyList());
    }

    @Test
    void generateAccessToken_shouldWork() {
        String token = jwtService.generateAccessToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void generateRefreshToken_shouldWork() {
        String token = jwtService.generateRefreshToken(userDetails);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void extractUsername_shouldReturnCorrectUsername() {
        String token = jwtService.generateAccessToken(userDetails);

        String username = jwtService.extractUsername(token);

        assertEquals("testuser", username);
    }

    @Test
    void isTokenValid_shouldReturnTrue_whenValidToken() {
        String token = jwtService.generateAccessToken(userDetails);

        boolean isValid = jwtService.isTokenValid(token, userDetails);

        assertTrue(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenWrongUser() {
        String token = jwtService.generateAccessToken(userDetails);

        UserDetails anotherUser = new User(
                "anotheruser",
                "password",
                Collections.emptyList());

        boolean isValid = jwtService.isTokenValid(token, anotherUser);

        assertFalse(isValid);
    }

    @Test
    void isTokenValid_shouldReturnFalse_whenExpiredToken() {
        // ✅ Ставим expiration = 0 → токен сразу истекает!
        ReflectionTestUtils.setField(jwtService, "accessTokenExpiration", 0L);

        String token = jwtService.generateAccessToken(userDetails);

        assertThrows(Exception.class,
                () -> jwtService.isTokenValid(token, userDetails));
    }

    @Test
    void accessToken_and_refreshToken_shouldBeDifferent() {
        String accessToken = jwtService.generateAccessToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        assertNotEquals(accessToken, refreshToken);
    }
}
