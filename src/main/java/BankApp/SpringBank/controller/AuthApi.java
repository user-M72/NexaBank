package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;
import BankApp.SpringBank.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/v1")
public class AuthApi {

    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody Login dto) {
        AuthResponseDto login = service.login(dto);
        return ResponseEntity.ok(login);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDto> register(@RequestBody Register dto) {
        AuthResponseDto register = service.register(dto);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponseDto> refresh(@RequestHeader("Refresh-Token") String refreshToken) {
        AuthResponseDto refresh = service.refresh(refreshToken);
        return ResponseEntity.ok(refresh);
    }
}
