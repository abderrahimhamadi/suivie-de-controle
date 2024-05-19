package com.example.miniprojetasedsinpt.controllers;

import com.example.miniprojetasedsinpt.dtos.PersonneDTO;
import com.example.miniprojetasedsinpt.security.AuthService;
import com.example.miniprojetasedsinpt.security.AuthenticationRequest;
import com.example.miniprojetasedsinpt.security.AuthenticationResponse;
import com.example.miniprojetasedsinpt.security.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/authentication")
@CrossOrigin("*")
public class AuthenticationController {
    private final AuthService authService;
    private final JwtService jwtservice;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody PersonneDTO personneDTO) {
        AuthenticationResponse authResponse = authService.register(personneDTO);
        if (authResponse.getErrorMessage() != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(authResponse);
        }
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest authenticationRequest) {
        AuthenticationResponse authResponse = authService.authenticate(authenticationRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/logout")
    public void logout(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        String jwt = authHeader.substring(7);
        jwtservice.setTokenExpirationToPast(jwt);
    }
}