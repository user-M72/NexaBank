package BankApp.SpringBank.controller;

import BankApp.SpringBank.dto.res.UserResponseDto;
import BankApp.SpringBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/v1")
public class UserApi {

    private final UserService service;

    @GetMapping
    public List<UserResponseDto> get(){
        return service.get();
    }
}
