package com.buddy.chat.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.buddy.chat.dto.request.UserLoginRequest;
import com.buddy.chat.dto.request.UserLogoutRequest;
import com.buddy.chat.dto.request.UserRegistrationRequest;
import com.buddy.chat.dto.response.UserLoginResponse;
import com.buddy.chat.dto.response.UserResponse;

import jakarta.mail.MessagingException;

public interface UserService extends UserDetailsService {

	public List<UserResponse> findAllUsers();

	public UserResponse findUserByUserId(Integer userId);

	public UserLoginResponse login(UserLoginRequest request);

	public void logout(UserLogoutRequest request);

	public UserLoginResponse GoogleLogin(String accessToken);

	public UserLoginResponse FacebookLogin(String accessToken);

	public UserResponse register(UserRegistrationRequest request)
			throws UnsupportedEncodingException, MessagingException;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

}
