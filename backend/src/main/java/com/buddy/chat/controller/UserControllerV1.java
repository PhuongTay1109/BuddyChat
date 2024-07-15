package com.buddy.chat.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserControllerV1 {
	
    private final UserService userService;
    
    @GetMapping()
    public ResponseEntity<ApiResponse> getUserList() {
    	ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("Get users list successfully")
    			.statusCode(HttpStatus.OK.value())
    			.data(userService.findAllUsers())
    			.build();
    	
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
    	ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("Get user successfully")
    			.statusCode(HttpStatus.OK.value())
    			.data(userService.loadUserByUsername(username))
    			.build();

        return new ResponseEntity<>(resp, HttpStatus.OK); 
    }
}