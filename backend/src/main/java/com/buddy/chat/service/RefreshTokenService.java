package com.buddy.chat.service;

import com.buddy.chat.dto.request.RefreshTokenRequest;
import com.buddy.chat.dto.response.RefreshTokenResponse;
import com.buddy.chat.model.RefreshToken;

public interface RefreshTokenService {
    RefreshToken findByUsername(String username);
    
    RefreshTokenResponse refreshToken(RefreshTokenRequest request);
}