package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.TokenPairDto;
import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;
import BankApp.SpringBank.model.User;

public interface AuthService {

    TokenPairDto login(Login dto);

    TokenPairDto register(Register dto);

    TokenPairDto refresh(String refreshToken);

    User getCurrentUser();
}
