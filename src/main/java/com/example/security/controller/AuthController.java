package com.example.security.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
public class AuthController {
    private static final  String SECRET_KEY = "sua-key";
    private static final long EXPIRATION_TIME = 86400000;

    @PostMapping("/login")
    public String login(@RequestBody Map<String,String> credentials){
        String username = credentials.get("username");
        String password = credentials.get("password");

        if (username.equals("admin") && password.equals("password123")){
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                    .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                    .compact();
        }
        throw new RuntimeException("Credenciais invalidas");
    }
}
