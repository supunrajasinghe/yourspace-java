package com.yourspace.yourspace.controller;

import com.yourspace.yourspace.model.AuthRequest;
import com.yourspace.yourspace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<String> login(
            final @RequestBody AuthRequest authRequest
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(authRequest));
    }
}
