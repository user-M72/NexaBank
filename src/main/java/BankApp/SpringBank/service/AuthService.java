package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;

public interface AuthService {

    AuthResponseDto login(Login dto);

    AuthResponseDto register(Register dto);

    AuthResponseDto refresh(String refreshToken);
}
