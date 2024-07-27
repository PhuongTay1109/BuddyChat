package com.buddy.chat.service.impl;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.buddy.chat.dto.request.UserUpdateRequest;
import com.buddy.chat.exception.AppException;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;

	@Override
	public List<User> findAllUsers() {
		List<User> usersList = userRepository.findAll();
		return usersList;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new AppException("User not found with username: " + username, HttpStatus.NOT_FOUND);
		}
		return user;
	}

	@Override
	public User findUserByUserId(Integer userId) {
		User user = userRepository.findByUserId(userId);
		if (user == null) {
			throw new AppException("User not found with id: " + userId, HttpStatus.NOT_FOUND);
		}
		return user;
	}

	@Override
	public User updateUser(Integer userId, UserUpdateRequest userCreationRequest) {
		User existingUser = userRepository.findByUserId(userId);
		if (existingUser == null) {
			throw new AppException("User not found with ID " + userId, HttpStatus.NOT_FOUND);
		}
		existingUser.setUsername(userCreationRequest.getUsername());
		existingUser.setFirstName(userCreationRequest.getFirstName());
		existingUser.setLastName(userCreationRequest.getLastName());
		return userRepository.save(existingUser);
	}

	@Override
	public void deleteUserById(Integer userId) {
		User existingUser = userRepository.findByUserId(userId);
		if (existingUser == null) {
			throw new AppException("User not found with ID " + userId, HttpStatus.NOT_FOUND);
		}
		userRepository.delete(existingUser);
	}

	@Override
	public List<User> searchUsers(String query) {
		return userRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query);
	}

}