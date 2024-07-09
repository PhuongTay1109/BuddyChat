package com.buddy.chat.controller;

import java.io.UnsupportedEncodingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.request.EmailTokenRequest;
import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.service.EmailVerificationTokenService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/verify")
@RequiredArgsConstructor
public class VerifyControllerV1 {
	
	private final EmailVerificationTokenService emailVerificationTokenService;

	@PostMapping("/confirm-account-registration")
	public ResponseEntity<ApiResponse> confirmAccountRegistration(@RequestBody EmailTokenRequest tokenDTO) throws UnsupportedEncodingException, MessagingException {
		
		boolean isVerified = emailVerificationTokenService.confirmAccountRegistration(tokenDTO.getToken());
		String message = isVerified ? "Account is confirmed successfully" : "Token expired. A new confirmation email has been sent.";
		int statusCode = isVerified ? HttpStatus.OK.value() : HttpStatus.ACCEPTED.value();

		ApiResponse resp = ApiResponse.builder()
				.statusCode(statusCode)
				.message(message)
				.build();
		return new ResponseEntity<>(resp, HttpStatus.valueOf(statusCode));
		
	}

}
	