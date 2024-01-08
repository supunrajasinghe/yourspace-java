package com.yourspace.yourspace.service;

import com.yourspace.yourspace.model.AuthRequest;
import com.yourspace.yourspace.model.JwtResponse;
import com.yourspace.yourspace.model.RefreshToken;
import com.yourspace.yourspace.model.RefreshTokenRequest;
import com.yourspace.yourspace.repository.RefreshTokenRepository;
import com.yourspace.yourspace.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.yourspace.yourspace.service.JwtService.SECRET;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public JwtResponse login(AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        if(!authenticate.isAuthenticated()) {
            throw new UsernameNotFoundException("Invalid user request");
        }

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authRequest.getUsername());
        String accessToken = generateToken(authRequest.getUsername());
        return JwtResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getToken())
                .build();
    }

    private String generateToken(String email) {
        Map<String,Object> claims = new HashMap<>();
        return createToken(claims,email);
    }

    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
                .signWith(generateSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key generateSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public JwtResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        return refreshTokenRepository.findByToken(refreshTokenRequest.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUserId)
                .map(userRepository::findById)
                .map(user -> {
                    if(user.isEmpty()) throw new RuntimeException("User not found");
                    return JwtResponse.builder()
                            .accessToken(generateToken(user.get().getEmail()))
                            .refreshToken(refreshTokenRequest.getToken())
                            .build();
                }).orElseThrow(() -> new RuntimeException("Token not found"));
    }
}
