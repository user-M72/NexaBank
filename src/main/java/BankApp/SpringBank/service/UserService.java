package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {

    List<UserResponseDto> get();

    UserResponseDto getById(UUID id);

    UserResponseDto create(UserRequestDto dto);

    UserResponseDto update(UUID id, UserRequestDto dto);

    void delete(UUID id);

    boolean existsByEmail(String email);

    User findById(UUID id);
}
