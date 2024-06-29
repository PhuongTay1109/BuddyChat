package com.buddy.chat.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.buddy.chat.exception.AppException;
import com.buddy.chat.model.RefreshToken;
import com.buddy.chat.repository.RefreshTokenRepository;
import com.buddy.chat.service.RefreshTokenService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;

    @Override
    public RefreshToken findByUsername(String username) {
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username);
        if (refreshToken == null) {
            throw new AppException("Refresh token doesn't exist with " + username, HttpStatus.UNAUTHORIZED);
        }
        return refreshToken;
    }

}