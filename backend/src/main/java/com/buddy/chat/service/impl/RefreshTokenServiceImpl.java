package com.buddy.chat.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.buddy.chat.dto.request.RefreshTokenRequest;
import com.buddy.chat.dto.response.RefreshTokenResponse;
import com.buddy.chat.exception.AppException;
import com.buddy.chat.model.RefreshToken;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.RefreshTokenRepository;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.RefreshTokenService;
import com.buddy.chat.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {
	
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    
    @Override
    public RefreshToken findByUsername(String username) {
        RefreshToken refreshToken = refreshTokenRepository.findByUsername(username);
        if (refreshToken == null) {
            throw new AppException("Refresh token doesn't exist with " + username, HttpStatus.UNAUTHORIZED);
        }
        return refreshToken;
    }

	@Override
	public RefreshTokenResponse refreshToken(RefreshTokenRequest request) {
		
		String username = jwtUtil.extractUsername(request.getRefreshToken());
		
		User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new AppException("User not found with username: " + username, HttpStatus.NOT_FOUND);
        }
        
        String storedRefreshToken = refreshTokenRepository.findByUsername(username).getToken();
        
        // compare refresh token in database and refresh token in user request
        // valid that refresh token
        if (storedRefreshToken.equals(request.getRefreshToken()) && jwtUtil.isTokenValid(request.getRefreshToken())) {
            return RefreshTokenResponse.builder()
	            		.newAccessToken(jwtUtil.generateAccessToken(user))
	            		.refreshToken(storedRefreshToken)
            		    .build();
        }

        throw new AppException("Invalid refresh token", HttpStatus.UNAUTHORIZED);
	}
}