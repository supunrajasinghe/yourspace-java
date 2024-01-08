package com.yourspace.yourspace.controller;

import com.yourspace.yourspace.model.AuthRequest;
import com.yourspace.yourspace.model.JwtResponse;
import com.yourspace.yourspace.model.RefreshTokenRequest;
import com.yourspace.yourspace.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<JwtResponse> login(
            final @RequestBody AuthRequest authRequest
            ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(authRequest));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<JwtResponse> refreshToken(
            final @RequestBody RefreshTokenRequest refreshTokenRequest
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(authService.refreshToken(refreshTokenRequest));
    }
}
