package com.buddy.chat.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.buddy.chat.dto.request.UserUpdateRequest;
import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.dto.response.UserResponse;
import com.buddy.chat.mapper.UserMapper;
import com.buddy.chat.model.User;
import com.buddy.chat.service.UserService;

import jakarta.validation.Valid;
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
    			.data(userService.findAllUsers().stream().map(user -> UserMapper.toUserResponse(user)).collect(Collectors.toList()))
    			.build();
    	
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }
    
	@GetMapping("/me")
	public ResponseEntity<ApiResponse> getUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("Get user successfully")
    			.statusCode(HttpStatus.OK.value())
    			.data(UserMapper.toUserResponse(user))
    			.build();
    	
        return new ResponseEntity<>(resp, HttpStatus.OK);
	}
	
	@GetMapping("/me/roles")
	public ResponseEntity<ApiResponse> getUserRoles() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)authentication.getPrincipal();
		ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("Get user roles successfully")
    			.statusCode(HttpStatus.OK.value())
    			.data(user.getRoles())
    			.build();
    	
        return new ResponseEntity<>(resp, HttpStatus.OK);
	}

    @GetMapping("/{username}")
    public ResponseEntity<ApiResponse> getUserByUsername(@PathVariable String username) {
		UserResponse userResponse = UserMapper.toUserResponse((User)userService.loadUserByUsername(username));
    	ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("Get user successfully")
    			.statusCode(HttpStatus.OK.value())
    			.data(userResponse)
    			.build();

        return new ResponseEntity<>(resp, HttpStatus.OK); 
    }
    
	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse> updateUser(@PathVariable Integer id, @Valid @RequestBody UserUpdateRequest userUpdateRequest ) {

		UserResponse userResponse = UserMapper.toUserResponse(userService.updateUser(id, userUpdateRequest));
		ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User updated")
    			.statusCode(HttpStatus.CREATED.value())
    			.data(userResponse)
    			.build();

        return new ResponseEntity<>(resp, HttpStatus.CREATED); 
	} 

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable Integer id) {
		userService.deleteUserById(id);

		ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("User deleted")
    			.statusCode(HttpStatus.OK.value())
    			.data(null)
    			.build();

        return new ResponseEntity<>(resp, HttpStatus.OK); 
	} 
	
	@GetMapping("/search")
    public ResponseEntity<ApiResponse> searchUsers(@RequestParam String query) {
		ApiResponse resp = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.statusCode(HttpStatus.OK.value())
    			.data(userService.searchUsers(query).stream().map(user -> UserMapper.toUserResponse(user)).collect(Collectors.toList()))
    			.build();
        return new ResponseEntity<>(resp, HttpStatus.OK);
    }

}