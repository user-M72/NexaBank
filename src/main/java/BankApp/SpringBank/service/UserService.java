package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.UserRequestDto;
import BankApp.SpringBank.dto.res.UserResponseDto;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponseDto> get();

    UserResponseDto getById(UUID id);

    UserResponseDto created(UserRequestDto dto);

    UserResponseDto updated(UUID id, UserRequestDto dto);

    void deleted(UUID id);

    boolean existsByEmail(String email);
}
