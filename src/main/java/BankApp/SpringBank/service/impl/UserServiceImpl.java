package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.exception.AccountNotFoundException;
import BankApp.SpringBank.exception.UserNotFoundException;
import BankApp.SpringBank.mapper.UserMapper;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.UserRepository;
import BankApp.SpringBank.service.RoleService;
import BankApp.SpringBank.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponseDto> get() {

        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getById(UUID id) {

        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        return mapper.toDto(user);
    }

    @Override
    public UserResponseDto created(UserRequestDto dto) {

        Set<Role> roleSet = roleService.geyByIdList(dto.roleId());
        User user = mapper.toEntity(dto, roleSet, passwordEncoder.encode(dto.password()));

        User save = repository.save(user);
        return mapper.toDto(save);
    }

    @Override
    public UserResponseDto updated(UUID id, UserRequestDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        Set<Role> roles = roleService.geyByIdList(dto.roleId());

        mapper.updateFromDto(dto, roles, user);
        User save = repository.save(user);

        return mapper.toDto(save);
    }

    @Override
    public void deleted(UUID id) {
        if (!repository.existsById(id)) {
            throw new UserNotFoundException(id);
        }
        repository.deleteById(id);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public User findById(UUID id) {
        return repository.findById(id)
                .orElseThrow(()-> new UserNotFoundException(id));
    }
}
