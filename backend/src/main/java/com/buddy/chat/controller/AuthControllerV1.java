package com.buddy.chat.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.request.OAuthTokenRequest;
import com.buddy.chat.dto.request.RefreshTokenRequest;
import com.buddy.chat.dto.request.UserLoginRequest;
import com.buddy.chat.dto.request.UserRegistrationRequest;
import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.dto.response.UserLoginResponse;
import com.buddy.chat.dto.response.UserResponse;
import com.buddy.chat.service.RefreshTokenService;
import com.buddy.chat.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerV1 {
	
	private final UserService userService;
	private final RefreshTokenService refreshTokenService;
	
	@PostMapping("/register") 
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegistrationRequest requestBody) throws UnsupportedEncodingException, MessagingException {
		UserResponse registeredUser = userService.register(requestBody);
    	ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User created successfully")
    			.statusCode(HttpStatus.CREATED.value())
    			.data(registeredUser)
    			.build();

        return new ResponseEntity<>(respponse, HttpStatus.CREATED); 
	}

	
	@PostMapping("/login")

	public ResponseEntity<ApiResponse> login(@Valid @RequestBody UserLoginRequest requestBody) {
		UserLoginResponse userLoginResponse = userService.login(requestBody);

		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is authenticated")
    			.statusCode(HttpStatus.OK.value())
    			.data(userLoginResponse)
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK); 
	}

	@PostMapping("/google-login")
	public ResponseEntity<ApiResponse> googleLogin(@Valid @RequestBody OAuthTokenRequest requestBody) {
		// 
		UserLoginResponse userLoginResponse = userService.googleLogin(requestBody.getToken());
		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is authenticated")
    			.statusCode(HttpStatus.OK.value())
    			.data(userLoginResponse)
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK); 

	}

	
	@PostMapping("/refresh-token")
	public ResponseEntity<ApiResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {

		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is authenticated")
    			.statusCode(HttpStatus.OK.value())
    			.data(refreshTokenService.refreshToken(request))
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK); 
	}
}