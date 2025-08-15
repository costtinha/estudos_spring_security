package com.example.security.controller;

import com.example.security.dto.TokenResponse;
import com.example.security.service.authService.AuthService;
import com.example.security.service.securityService.TokenService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AuthController {
    private final TokenService service;
    private final AuthService authService;

    public AuthController(TokenService service, AuthService authService) {
        this.service = service;
        this.authService = authService;
    }

    @PostMapping("/login")
    public TokenResponse login(@RequestBody Map<String,String> credentials){
        return authService.login(credentials);
       // String username = credentials.get("username");
        //String password = credentials.get("password");
       // if (username.equals("admin") && password.equals("password123")){
          //  String accessToken = service.generateAccessToken(username);
           // String refreshToken = service.generateRefreshToken(username);

          //  return new TokenResponse(accessToken,refreshToken);
     //   }
       // throw new RuntimeException("Credenciais invalidas");
    }

    @PostMapping("/register")
    public String register(@RequestBody Map<String,String> credentials){
        return authService.register(credentials);
    }

    @PostMapping("/refresh")
    public TokenResponse refresh(@RequestBody Map<String,String> credentials){
        return authService.refresh(credentials);
       // String refreshToken = credentials.get("refreshToken");
      //  try {
        //    String username = service.validateTokenAndGetUsername(refreshToken);
          //  String newAccessToken = service.generateAccessToken(username);
           // return new TokenResponse(newAccessToken,refreshToken);
      //  } catch (Exception e) {
           // throw new RuntimeException("Refresh token inspirado ou invalido");
     //   }

    }
}
