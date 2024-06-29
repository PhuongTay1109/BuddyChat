package com.buddy.chat.service;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.buddy.chat.dto.request.UserRegistrationDTO;
import com.buddy.chat.dto.response.UserResponseDTO;

import jakarta.mail.MessagingException;


public interface UserService extends UserDetailsService {

    public List<UserResponseDTO> findAllUsers();
    public UserResponseDTO findUserByUserId(Integer userId);
    
    public UserResponseDTO register(UserRegistrationDTO request) throws UnsupportedEncodingException, MessagingException;

    @Override
    default UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'loadUserByUsername'");
    }
}
