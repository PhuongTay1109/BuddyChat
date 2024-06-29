package com.buddy.chat.controller;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.request.UserRegistrationDTO;
import com.buddy.chat.dto.response.ResponseDTO;
import com.buddy.chat.dto.response.UserResponseDTO;
import com.buddy.chat.service.UserService;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthControllerV1 {
	
	private final UserService userService;
	
	@PostMapping("/register") 
	public ResponseEntity<ResponseDTO> register(@Valid @RequestBody UserRegistrationDTO requestBody) throws UnsupportedEncodingException, MessagingException {
		UserResponseDTO registeredUser = userService.register(requestBody);
    	ResponseDTO resp = ResponseDTO.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User created successfully")
    			.statusCode(HttpStatus.CREATED.value())
    			.data(registeredUser)
    			.build();

        return new ResponseEntity<>(resp, HttpStatus.CREATED); 
	}
}
