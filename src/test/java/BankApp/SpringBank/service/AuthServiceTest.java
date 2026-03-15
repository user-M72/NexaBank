package BankApp.SpringBank.service;

import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;
import BankApp.SpringBank.exception.*;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.RoleRepository;
import BankApp.SpringBank.repository.UserRepository;
import BankApp.SpringBank.service.impl.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @InjectMocks
    AuthServiceImpl authService;

    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserRepository userRepository;
    @Mock private RoleRepository roleRepository;
    @Mock private JwtService jwtService;
    @Mock private AuthenticationManager authenticationManager;
    private User user;
    private Role userRole;

    @BeforeEach
    void setUp() {
        userRole = new Role();
        userRole.setName("USER");

        user = new User();
        user.setUsername("test");
        user.setPassword("encodedPassword");
        user.setEmail("test@test.com");
        user.setRoles(new HashSet<>(Set.of(userRole)));
    }

    @Test
    void login_shouldWork() {
        Login dto = new Login("test", "test");

        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken("test", "test"));
        when(userRepository.findByUsername("test"))
                .thenReturn(Optional.of(user));
        when(jwtService.generateAccessToken(any(UserDetails.class)))
                .thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any(UserDetails.class)))
                .thenReturn("refreshToken");

        AuthResponseDto result = authService.login(dto);

        assertEquals("accessToken", result.accessToken());
        assertEquals("refreshToken", result.refreshToken());
        verify(authenticationManager).authenticate(any());
        verify(userRepository).findByUsername("test");
        verify(jwtService).generateAccessToken(any());
        verify(jwtService).generateRefreshToken(any());
    }

    @Test
    void login_userNotFound() {
        Login dto = new Login("unknown", "test");

        when(authenticationManager.authenticate(any()))
                .thenReturn(new UsernamePasswordAuthenticationToken("unknown", "test"));
        when(userRepository.findByUsername("unknown"))
                .thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> authService.login(dto));

        verify(jwtService, never()).generateAccessToken(any());
        verify(jwtService, never()).generateRefreshToken(any());
    }

    @Test
    void login_wrongPassword() {
        Login dto = new Login("test", "wrongPassword");

        when(authenticationManager.authenticate(any()))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        assertThrows(BadCredentialsException.class,
                () -> authService.login(dto));

        verify(userRepository, never()).findByUsername(any());
        verify(jwtService, never()).generateAccessToken(any());
    }

    @Test
    void register_shouldWork() {
        Register dto = new Register(
                "John", "Doe", "johndoe", "john@test.com", "password123"
        );

        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("john@test.com")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(userRole));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);
        when(jwtService.generateAccessToken(any())).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("refreshToken");

        AuthResponseDto result = authService.register(dto);

        assertEquals("accessToken", result.accessToken());
        assertEquals("refreshToken", result.refreshToken());
        verify(userRepository).save(any());
    }

    @Test
    void register_usernameAlreadyExists() {
        Register dto = new Register(
                "John", "Doe", "test", "john@test.com", "password123"
        );

        when(userRepository.existsByUsername("test")).thenReturn(true);

        assertThrows(UsernameAlreadyExistsException.class,
                () -> authService.register(dto));

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_emailAlreadyExists() {
        Register dto = new Register(
                "John", "Doe", "johndoe", "test@test.com", "password123"
        );

        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        assertThrows(EmailAlreadyExistsException.class,
                () -> authService.register(dto));

        verify(userRepository, never()).save(any());
    }

    @Test
    void register_roleNotFound() {
        Register dto = new Register(
                "John", "Doe", "johndoe", "john@test.com", "password123"
        );

        when(userRepository.existsByUsername("johndoe")).thenReturn(false);
        when(userRepository.existsByEmail("john@test.com")).thenReturn(false);
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundByNameException.class,
                () -> authService.register(dto));

        verify(userRepository, never()).save(any());
    }


    @Test
    void refresh_shouldWork() {
        String refreshToken = "validRefreshToken";

        when(jwtService.extractUsername(refreshToken)).thenReturn("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);
        when(jwtService.generateAccessToken(any())).thenReturn("newAccessToken");
        when(jwtService.generateRefreshToken(any())).thenReturn("newRefreshToken");

        AuthResponseDto result = authService.refresh(refreshToken);

        assertEquals("newAccessToken", result.accessToken());
        assertEquals("newRefreshToken", result.refreshToken());
    }

    @Test
    void refresh_invalidToken() {
        String refreshToken = "invalidToken";

        when(jwtService.extractUsername(refreshToken)).thenReturn("test");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(any(), any())).thenReturn(false);

        assertThrows(RefreshTokenInvalidException.class,
                () -> authService.refresh(refreshToken));

        verify(jwtService, never()).generateAccessToken(any());
    }

    @Test
    void refresh_userNotFound() {
        String refreshToken = "validToken";

        when(jwtService.extractUsername(refreshToken)).thenReturn("unknown");
        when(userRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class,
                () -> authService.refresh(refreshToken));
    }
}
