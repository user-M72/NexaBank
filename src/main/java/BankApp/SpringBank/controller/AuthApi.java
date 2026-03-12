package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;
import BankApp.SpringBank.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
@Tag(name = "Auth-Api", description = "API for managing authentication and authorization in the banking application")
public class AuthApi {

    private final AuthService service;

    @Operation(summary = "Login", description = "Authenticate a user and generate access and refresh tokens")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody Login dto) {
        AuthResponseDto login = service.login(dto);
        return ResponseEntity.ok(login);
    }

    @Operation(summary = "Register", description = "Register a new user and generate access and refresh tokens")
    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody Register dto) {
        AuthResponseDto register = service.register(dto);
        return ResponseEntity.ok(register);
    }

    @Operation(summary = "Refresh Token", description = "Refresh")
    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        AuthResponseDto refresh = service.refresh(refreshToken);
        return ResponseEntity.ok(refresh);
    }
}
