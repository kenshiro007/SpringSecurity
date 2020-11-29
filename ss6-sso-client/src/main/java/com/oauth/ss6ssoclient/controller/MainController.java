package com.oauth.ss6ssoclient.controller;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping
    public String main(AuthenticationManager authenticationManager) {
        return "main.html";
    }
}
