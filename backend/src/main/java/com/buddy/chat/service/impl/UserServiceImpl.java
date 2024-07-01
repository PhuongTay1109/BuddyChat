package com.buddy.chat.service.impl;


import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buddy.chat.dto.request.UserRegistrationDTO;
import com.buddy.chat.dto.response.UserResponseDTO;
import com.buddy.chat.exception.AppException;
import com.buddy.chat.mapper.UserMapper;
import com.buddy.chat.model.Role;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.RoleRepository;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.EmailVerificationTokenService;
import com.buddy.chat.service.UserService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EmailVerificationTokenService emailVerificationTokenService;


    @Override
    public List<UserResponseDTO> findAllUsers() {
    	List<User> usersList = userRepository.findAll();
    	return usersList.stream()
                .map(user -> userMapper.toUserResponse(user))
                .collect(Collectors.toList());
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
    public UserResponseDTO findUserByUserId(Integer userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new AppException("User not found with id: " + userId, HttpStatus.NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }

	@Override
	public UserResponseDTO register(UserRegistrationDTO request) throws UnsupportedEncodingException, MessagingException {
		
		String username = request.getUsername();
		String email = request.getEmail();
		
		if(userRepository.existsByUsername(username)) {
			throw new AppException("User with username " +  username + " existed", HttpStatus.CONFLICT);
		}
		
		if(userRepository.existsByEmail(email)) {
			throw new AppException("User with email " +  email + " existed", HttpStatus.CONFLICT);
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByAuthority("ROLE_USER"));
		
		User user = User.builder()
				.lastName(request.getLastName())
				.firstName(request.getFirstName())
				.email(email)
				.username(username)
				.password(encodedPassword)
				.profilePicture(request.getProfilePicture())
				.status(null)
				.isEnabled(false)
				.roles(roles)
				.build();
		
		userRepository.save(user); // save user to database
		
		emailVerificationTokenService.sendEmailConfirmation(email); // send confirmation mail
		
		return userMapper.toUserResponse(user);
	}

}
