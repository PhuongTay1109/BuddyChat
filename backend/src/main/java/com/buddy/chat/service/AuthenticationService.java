package com.buddy.chat.service;

import java.io.UnsupportedEncodingException;

import com.buddy.chat.dto.request.UserLoginRequest;
import com.buddy.chat.dto.request.UserLogoutRequest;
import com.buddy.chat.dto.request.UserRegistrationRequest;
import com.buddy.chat.dto.response.UserLoginResponse;
import com.buddy.chat.model.User;

import jakarta.mail.MessagingException;

public interface AuthenticationService {
    public UserLoginResponse login(UserLoginRequest request);
    public void logout(UserLogoutRequest request);
    public UserLoginResponse GoogleLogin(String accessToken);
    public UserLoginResponse FacebookLogin(String accessToken);
    public User register(UserRegistrationRequest request) throws UnsupportedEncodingException, MessagingException;
}
