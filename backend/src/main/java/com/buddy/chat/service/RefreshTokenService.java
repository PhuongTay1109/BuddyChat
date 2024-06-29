package com.buddy.chat.service;

import com.buddy.chat.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByUsername(String username);
}