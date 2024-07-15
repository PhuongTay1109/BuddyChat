package com.buddy.chat.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.request.OAuthTokenRequest;
import com.buddy.chat.dto.request.RefreshTokenRequest;
import com.buddy.chat.dto.request.UserLoginRequest;
import com.buddy.chat.dto.request.UserLogoutRequest;
import com.buddy.chat.dto.request.UserRegistrationRequest;
import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.dto.response.UserLoginResponse;
import com.buddy.chat.dto.response.UserResponse;
import com.buddy.chat.service.EmailVerificationTokenService;
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
	private final EmailVerificationTokenService emailService;
	
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

	@DeleteMapping("/logout")
	public ResponseEntity<ApiResponse> logout(@Valid @RequestBody UserLogoutRequest requestBody) {
		userService.logout(requestBody);

		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is logged out")
    			.statusCode(HttpStatus.NO_CONTENT.value())
    			.data(null)
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.NO_CONTENT); 
	}

	@PostMapping("/google-login")
	public ResponseEntity<ApiResponse> GoogleLogin(@Valid @RequestBody OAuthTokenRequest requestBody) {
		// 
		UserLoginResponse userLoginResponse = userService.GoogleLogin(requestBody.getToken());
		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is authenticated")
    			.statusCode(HttpStatus.OK.value())
    			.data(userLoginResponse)
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK); 

	}
	@PostMapping("/facebook-login")
	public ResponseEntity<ApiResponse> FacebookLogin(@Valid @RequestBody OAuthTokenRequest requestBody) {
		UserLoginResponse userLoginResponse = userService.FacebookLogin(requestBody.getToken());
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
		System.out.println("refresh token controller");
		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is authenticated")
    			.statusCode(HttpStatus.OK.value())
    			.data(refreshTokenService.refreshToken(request))
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK); 
	}
	
	@PostMapping("/forgot-password")
	public ResponseEntity<ApiResponse> sendPasswordResetEmail(@RequestParam String username) {

		emailService.sendPasswordResetEmail(username);

        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Password reset email sent successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
	}	

}