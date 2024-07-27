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
import com.buddy.chat.mapper.UserMapper;
import com.buddy.chat.model.User;
import com.buddy.chat.service.AuthenticationService;
import com.buddy.chat.service.EmailVerificationTokenService;
import com.buddy.chat.service.RefreshTokenService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerV1 {
	
	private final RefreshTokenService refreshTokenService;
	private final EmailVerificationTokenService emailService;
	private final AuthenticationService authenticationService;
	
	@PostMapping("/register") 
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegistrationRequest requestBody) throws UnsupportedEncodingException, MessagingException {
		UserResponse registeredUser = UserMapper.toUserResponse( authenticationService.register(requestBody));
		System.out.println(registeredUser.toString());
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
		UserLoginResponse userLoginResponse =  authenticationService.login(requestBody);

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
		 authenticationService.logout(requestBody);

		ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User is logged out")
    			.statusCode(HttpStatus.NO_CONTENT.value())
    			.data(null)
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK); 
	}

	@PostMapping("/google-login")
	public ResponseEntity<ApiResponse> GoogleLogin(@Valid @RequestBody OAuthTokenRequest requestBody) {
		// 
		UserLoginResponse userLoginResponse =  authenticationService.GoogleLogin(requestBody.getToken());
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
		UserLoginResponse userLoginResponse =  authenticationService.FacebookLogin(requestBody.getToken());
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
	public ResponseEntity<ApiResponse> sendPasswordResetEmail(@RequestParam String email) {
		
		User user = emailService.checkPasswordResetEmail(email);

		emailService.sendPasswordResetEmail(user);

        ApiResponse response = ApiResponse.builder()
                .timestamp(LocalDateTime.now())
                .message("Password reset email sent successfully")
                .statusCode(HttpStatus.OK.value())
                .build();
        
        return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

}