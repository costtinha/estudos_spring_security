package com.example.security.service.authService;

import com.example.security.dto.TokenResponse;
import com.example.security.entity.UserEntity;
import com.example.security.persistance.UserEntityRepository;
import com.example.security.service.securityService.CustomUserDetailsService;
import com.example.security.service.securityService.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;
    private final UserEntityRepository userEntityRepository;
    private final PasswordEncoder passwordEncoder;


    public AuthService(TokenService tokenService, CustomUserDetailsService userDetailsService, UserEntityRepository userEntityRepository, PasswordEncoder passwordEncoder) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
        this.userEntityRepository = userEntityRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String register(Map<String, String> credentials) {
        String username = credentials.get("username");
        String rawPassword = credentials.get("password");
        String hashedPassword = passwordEncoder.encode(rawPassword);

        UserEntity newUser = new UserEntity();

        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        newUser.setRole("USER");
        userEntityRepository.save(newUser);
        return "Usuario registrado com sucesso";


    }


    public TokenResponse login(Map<String, String> credentials) {
        String username = credentials.get("username");
        String rawPassword = credentials.get("password");
        var userDetails = userDetailsService.loadUserByUsername(username);
        if(!passwordEncoder.matches(rawPassword,userDetails.getPassword())){
            throw new RuntimeException("Senha incorreta");
        }
        String accessToken = tokenService.generateAccessToken(username);
        String refreshToken = tokenService.generateRefreshToken(username);
        return new TokenResponse(accessToken,refreshToken);
    }

    public TokenResponse refresh(Map<String, String> credentials) {
        String refreshToken = credentials.get("refreshToken");
        try {
            String username = tokenService.validateTokenAndGetUsername(refreshToken);
            String newAccessToken = tokenService.generateAccessToken(username);
            return new TokenResponse(newAccessToken,refreshToken);
        }
        catch (Exception e){
            throw new RuntimeException("Refresh token inspirado ou invalido");
        }
    }
}
