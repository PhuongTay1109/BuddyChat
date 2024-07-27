package com.buddy.chat.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.buddy.chat.dto.request.UserLoginRequest;
import com.buddy.chat.dto.request.UserLogoutRequest;
import com.buddy.chat.dto.request.UserRegistrationRequest;
import com.buddy.chat.dto.request.UserUpdateRequest;
import com.buddy.chat.dto.response.UserLoginResponse;
import com.buddy.chat.model.User;

import jakarta.mail.MessagingException;

public interface UserService extends UserDetailsService {

	 public List<User> findAllUsers();
	    public User findUserByUserId(Integer userId);
	    public User updateUser(Integer userId, UserUpdateRequest userCreationRequest);
	    public void deleteUserById(Integer userId);
	    public List<User> searchUsers(String query);

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
	    

}
