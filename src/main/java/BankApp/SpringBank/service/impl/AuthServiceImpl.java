package BankApp.SpringBank.service.impl;

import BankApp.SpringBank.config.CustomUserDetails;
import BankApp.SpringBank.dto.req.auth.Login;
import BankApp.SpringBank.dto.req.auth.Register;
import BankApp.SpringBank.dto.res.auth.AuthResponseDto;
import BankApp.SpringBank.exception.*;
import BankApp.SpringBank.model.Role;
import BankApp.SpringBank.model.User;
import BankApp.SpringBank.repository.RoleRepository;
import BankApp.SpringBank.repository.UserRepository;
import BankApp.SpringBank.service.AuthService;
import BankApp.SpringBank.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponseDto login(Login dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );

        User user = userRepository.findByUsername(dto.username())
                .orElseThrow(() -> new UsernameNotFoundException(dto.username()));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return generateTokens(userDetails);

    }

    @Override
    @Transactional
    public AuthResponseDto register(Register dto) {

        if (userRepository.existsByUsername(dto.username())) {
            throw new UsernameAlreadyExistsException(dto.username());
        }

        if (userRepository.existsByEmail(dto.email())) {
            throw new EmailAlreadyExistsException(dto.email());
        }

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RoleNotFoundByNameException("USER"));

        User user = User.builder()
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .username(dto.username())
                .email(dto.email())
                .password(passwordEncoder.encode(dto.password()))
                .roles(Set.of(userRole))
                .build();

        userRepository.save(user);

        CustomUserDetails userDetails = new CustomUserDetails(user);

        return generateTokens(userDetails);

    }

    @Override
    public AuthResponseDto refresh(String refreshToken) {

        String username = jwtService.extractUsername(refreshToken);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(null));

        CustomUserDetails userDetails = new CustomUserDetails(user);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new RefreshTokenInvalidException();
        }

        return generateTokens(userDetails);

    }

    private AuthResponseDto generateTokens(CustomUserDetails userDetails) {

        String newAccessToken = jwtService.generateAccessToken(userDetails);
        String newRefreshToken = jwtService.generateRefreshToken(userDetails);

        return new AuthResponseDto(newAccessToken, newRefreshToken);

    }
}
