package com.buddy.chat.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.request.EmailTokenRequest;
import com.buddy.chat.dto.request.ResetPasswordRequest;
import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.service.EmailVerificationTokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verification")
@RequiredArgsConstructor
public class VerificationControllerV1 {
	
	private final EmailVerificationTokenService emailVerificationTokenService;
	
	@PostMapping("/account-registration")
	public ResponseEntity<ApiResponse> confirmAccountRegistration(@Valid @RequestBody EmailTokenRequest tokenDTO) {
		boolean isVerified = emailVerificationTokenService.confirmAccountRegistration(tokenDTO.getToken());
		String message = isVerified ? "Account is confirmed successfully" : "Reset password link is expired. A new confirmation email has been sent.";
		int statusCode = isVerified ? HttpStatus.OK.value() : HttpStatus.ACCEPTED.value();

		ApiResponse response = ApiResponse.builder()
				.statusCode(statusCode)
				.message(message)
				.build();
		return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
	}
	
	@PostMapping("/reset-password")
	public ResponseEntity<ApiResponse> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
		boolean isSuccess = emailVerificationTokenService.resetPassword(request.getToken(), request.getPassword());
		String message = isSuccess ? "Password is changed successfully" : "Token expired. A new reset password email has been sent.";
		int statusCode = isSuccess ? HttpStatus.OK.value() : HttpStatus.ACCEPTED.value();

		ApiResponse response = ApiResponse.builder()
				.statusCode(statusCode)
				.message(message)
				.build();
		return new ResponseEntity<>(response, HttpStatus.valueOf(statusCode));
	}

}