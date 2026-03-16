package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.user.UserRequestDto;
import BankApp.SpringBank.dto.res.user.UserResponseDto;
import BankApp.SpringBank.exception.UserNotFoundException;
import BankApp.SpringBank.mapper.UserMapper;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.UserRepository;
import BankApp.SpringBank.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserServiceImpl testService;

    @Mock private UserRepository repository;
    @Mock private UserMapper mapper;
    @Mock private RoleService roleService;
    @Mock private PasswordEncoder passwordEncoder;

    private final UUID userId = UUID.randomUUID();
    private UserResponseDto response;
    private UserRequestDto request;
    private User user;
    private Set<Role> roles;
    private String password;

    @BeforeEach
    void setup(){

        user = new User();
        roles = new HashSet<>();
        password = "password";

        response = new UserResponseDto(
                userId,
                "FirstName",
                "LastName",
                "username",
                "email",
                password,
                Set.of()
        );

        request = new UserRequestDto(
                "FirstName",
                "LastName",
                "username",
                "email",
                password,
                List.of()
        );
    }

    @Test
    void get_shouldWork() {
        List<User> users = List.of(user);

        List<UserResponseDto> expected = List.of(response);

        when(repository.findAll()).thenReturn(users);
        when(mapper.toDto(user)).thenReturn(response);

        List<UserResponseDto> actual = testService.get();

        assertEquals(expected, actual);

        verify(repository).findAll();
        verify(mapper).toDto(user);

    }

    @Test
    void getById_shouldWork(){

        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(response);

        UserResponseDto actual = testService.getById(userId);

        assertEquals(response, actual);

        verify(repository).findById(userId);
        verify(mapper).toDto(user);

    }

    @Test
    void getById_shouldThrow_userNorFound(){

        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                ()-> testService.getById(userId));

        verify(repository).findById(userId);
        verify(mapper, never()).toDto(any());

    }

    @Test
    void create_shouldWork(){

        when(roleService.getByIdList(request.roleId())).thenReturn(roles);
        when(passwordEncoder.encode(request.password())).thenReturn(password);
        when(mapper.toEntity(request, roles, password)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(response);

        UserResponseDto actual = testService.create(request);

        assertEquals(response, actual);

        verify(roleService).getByIdList(request.roleId());
        verify(passwordEncoder).encode(request.password());
        verify(mapper).toEntity(request, roles, password);
        verify(repository).save(user);
        verify(mapper).toDto(user);

    }

    @Test
    void update_shouldWork(){

        when(repository.findById(userId)).thenReturn(Optional.of(user));
        when(roleService.getByIdList(request.roleId())).thenReturn(roles);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(response);

        UserResponseDto actual = testService.update(userId, request);

        assertEquals(response, actual);

        verify(repository).findById(userId);
        verify(roleService).getByIdList(request.roleId());
        verify(mapper).updateFromDto(request, roles, user);
        verify(repository).save(user);
        verify(mapper).toDto(user);
    }

    @Test
    void update_shouldThrow_userNotFound(){

        when(repository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                ()-> testService.update(userId, request));

        verify(repository).findById(userId);
        verify(roleService, never()).getByIdList(any());
        verify(mapper, never()).updateFromDto(any(), any(), any());
        verify(repository, never()).save(any());
    }

    @Test
    void delete_shouldWork(){

        when(repository.existsById(userId)).thenReturn(true);

        testService.delete(userId);

        verify(repository).existsById(userId);
        verify(repository).deleteById(userId);
    }

    @Test
    void delete_shouldThrow_userNorFound(){

        when(repository.existsById(userId)).thenReturn(false);

        assertThrows(UserNotFoundException.class,
                ()-> testService.delete(userId));

        verify(repository).existsById(userId);
    }
}
