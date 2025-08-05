package com.example.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/hello")
    public String hello(){
        return "Hello world secreto";
    }

    @GetMapping("/public/hello")
    public String helloPublic(){
        return "Hello world public";
    }
}
