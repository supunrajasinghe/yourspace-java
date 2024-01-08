package com.yourspace.yourspace.service;

import com.yourspace.yourspace.model.RefreshToken;
import com.yourspace.yourspace.model.User;
import com.yourspace.yourspace.repository.RefreshTokenRepository;
import com.yourspace.yourspace.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;

    public RefreshToken createRefreshToken(final String userName) {

        User byEmail = userRepository.findByEmail(userName).orElseThrow(
                () -> new UsernameNotFoundException("User not found"));

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(byEmail.getId())
                .token(UUID.randomUUID().toString())
                .ExpirationTime(Instant.now().plusSeconds(60*60*24))
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if(token.getExpirationTime().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException(token.getToken() + "refresh token expired");
        }
        return token;
    }
}
