package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.TokenPairDto;
import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;
import BankApp.SpringBank.service.AuthService;
import BankApp.SpringBank.service.CookieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
@Tag(name = "Auth-Api", description = "API for managing authentication and authorization in the banking application")
public class AuthApi {

    private final AuthService service;
    private final CookieService cookieService;

    @Operation(summary = "Login", description = "Authenticate a user and generate access and refresh tokens")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(
            @RequestBody Login dto,
            HttpServletResponse response) {

        TokenPairDto token = service.login(dto);

        cookieService.setRefreshTokenCookie(response, token.refreshToken());

        return ResponseEntity.ok(new AuthResponseDto(token.accessToken()));
    }

    @Operation(summary = "Register", description = "Register a new user and generate access and refresh tokens")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(
            @RequestBody Register dto,
            HttpServletResponse response) {

        TokenPairDto token = service.register(dto);

        cookieService.setRefreshTokenCookie(response, token.refreshToken());

        return ResponseEntity.ok(new AuthResponseDto(token.accessToken()));
    }

    @Operation(summary = "Refresh Token", description = "Refresh")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(
            @CookieValue(name = "refreshToken", required = false)
            String refreshToken,
            HttpServletResponse response) {

        if (refreshToken == null || refreshToken.isBlank()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        TokenPairDto refresh = service.refresh(refreshToken);

        cookieService.setRefreshTokenCookie(response, refresh.refreshToken());

        return ResponseEntity.ok(new AuthResponseDto(refresh.accessToken()));
    }
}
