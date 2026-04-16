package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.dto.req.user.ProfileUpdatePasswordDto;
import BankApp.SpringBank.dto.req.user.ProfileUpdateRequestDto;
import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.ProfileResponseDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.exception.CurrentPasswordException;
import BankApp.SpringBank.exception.PasswordNotMachException;
import BankApp.SpringBank.exception.UserNotFoundException;
import BankApp.SpringBank.mapper.ProfileMapper;
import BankApp.SpringBank.mapper.UserMapper;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.UserRepository;
import BankApp.SpringBank.service.AuthService;
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
    private final AuthService authService;
    private final ProfileMapper profileMapper;

    @Override
    public List<UserResponseDto> get() {

        return repository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserResponseDto getById(UUID id) {
        User user = findById(id);
        return mapper.toDto(user);
    }

    @Override
    public UserResponseDto create(UserRequestDto dto) {

        Set<Role> roleSet = roleService.getByIdList(dto.roleId());
        User user = mapper.toEntity(dto, roleSet, passwordEncoder.encode(dto.password()));
        User save = repository.save(user);
        return mapper.toDto(save);
    }

    @Override
    public UserResponseDto update(UUID id, UserRequestDto dto) {
        User user = findById(id);
        Set<Role> roles = roleService.getByIdList(dto.roleId());

        mapper.updateFromDto(dto, roles, user);
        User save = repository.save(user);

        return mapper.toDto(save);
    }

    @Override
    public void delete(UUID id) {
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

    @Override
    public ProfileResponseDto getMyProfile() {
        User user = authService.getCurrentUser();

        return profileMapper.toDto(user);
    }

    @Override
    public ProfileResponseDto updateMyProfile(ProfileUpdateRequestDto dto) {
        User user = authService.getCurrentUser();

        if (dto.firstName() != null && !dto.firstName().isBlank()) user.setFirstName(dto.firstName());
        if (dto.lastName() != null && !dto.lastName().isBlank()) user.setLastName(dto.lastName());
        if (dto.email() != null && !dto.email().isBlank()) user.setEmail(dto.email());
        if (dto.username() != null && !dto.username().isBlank()) user.setUsername(dto.username());

        User save = repository.save(user);
        return profileMapper.toDto(save);
    }

    @Override
    public ProfileResponseDto changeMyPassword(ProfileUpdatePasswordDto dto) {
        User user = authService.getCurrentUser();

        if (!passwordEncoder.matches(dto.currentPassword(), user.getPassword())) {
            throw new CurrentPasswordException();
        }

        if (!dto.newPassword().equals(dto.confirmPassword())){
            throw new PasswordNotMachException(dto.newPassword());
        }

        user.setPassword(passwordEncoder.encode(dto.newPassword()));
        User save = repository.save(user);
        return profileMapper.toDto(save);
    }
}
