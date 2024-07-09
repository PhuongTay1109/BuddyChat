package com.buddy.chat.service.impl;


import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.buddy.chat.dto.request.UserLoginRequest;
import com.buddy.chat.dto.request.UserRegistrationRequest;
import com.buddy.chat.dto.response.GoogleUserInfoResponse;
import com.buddy.chat.dto.response.UserLoginResponse;
import com.buddy.chat.dto.response.UserResponse;
import com.buddy.chat.enums.Provider;
import com.buddy.chat.exception.AppException;
import com.buddy.chat.mapper.UserMapper;
import com.buddy.chat.model.RefreshToken;
import com.buddy.chat.model.Role;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.RefreshTokenRepository;
import com.buddy.chat.repository.RoleRepository;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.EmailVerificationTokenService;
import com.buddy.chat.service.UserService;
import com.buddy.chat.util.JwtUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
	
	private final GoogleIdTokenVerifier googleIdTokenVerifier;
	private final JwtUtil jwtUtil;
	private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final EmailVerificationTokenService emailVerificationTokenService;

	private UserLoginResponse getUserLoginResponse(User user) {
		String username = user.getUsername();
		String accessToken = jwtUtil.generateAccessToken(user);
		String refreshToken = jwtUtil.generateRefreshToken(user);
		
		// Delete old token then create and store refreshTokenModel in Server
		RefreshToken existingRefreshToken = refreshTokenRepository.findByUsername(username);
		if (existingRefreshToken != null) {
			refreshTokenRepository.deleteByUsername(username);;
		}
		RefreshToken refreshTokenModel = new RefreshToken(username, refreshToken);
		refreshTokenRepository.save(refreshTokenModel); 
		UserLoginResponse userLoginResponse = UserLoginResponse.builder()
											.user(userMapper.toUserResponse(user))
											.accessToken(accessToken)
											.refreshToken(refreshToken)
											.build();
		return userLoginResponse;
	}


    @Override
    public List<UserResponse> findAllUsers() {
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
    public UserResponse findUserByUserId(Integer userId) {
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            throw new AppException("User not found with id: " + userId, HttpStatus.NOT_FOUND);
        }
        return userMapper.toUserResponse(user);
    }

	@Override
	public UserResponse register(UserRegistrationRequest request) throws UnsupportedEncodingException, MessagingException {
		
		if(userRepository.existsByUsername(request.getUsername())) {
			throw new AppException("User with username " +  request.getUsername() + " existed", HttpStatus.CONFLICT);
		}
		
		if(userRepository.existsByEmail(request.getEmail())) {
			throw new AppException("User with email " +  request.getEmail() + " existed", HttpStatus.CONFLICT);
		}
		
		String encodedPassword = passwordEncoder.encode(request.getPassword());
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByAuthority("ROLE_USER"));
		
		User user = User.builder()
				.lastName(request.getLastName())
				.firstName(request.getFirstName())
				.email(request.getEmail())
				.username(request.getUsername())
				.password(encodedPassword)
				.profilePicture(request.getProfilePicture())
				.status(null)
				.isEnabled(false)
				.provider(request.getProvider())
				.roles(roles)
				.build();
		
		userRepository.save(user);
		
		emailVerificationTokenService.sendEmailConfirmation(request.getEmail());
		
		return userMapper.toUserResponse(user);
	}

	@Override
	public UserLoginResponse login(UserLoginRequest request) {
		System.out.println("login service");
		String username = request.getUsername();
		String rawPassword = request.getPassword();
		User user = (User)loadUserByUsername(username); 
		
		if (!user.isEnabled()) {
			throw new AppException("Please check your email to verify your account", HttpStatus.UNAUTHORIZED);
		}
		if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
			throw new AppException("Invalid username or password", HttpStatus.UNAUTHORIZED);
		}
		return getUserLoginResponse(user);
	}

	@Override
	public UserLoginResponse googleLogin(String accessToken) {
		final String googleUserInfoEndpoint = "https://www.googleapis.com/oauth2/v3/userinfo";
		RestTemplate restTemplate = new RestTemplate();

        // Set up headers
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Set up request entity
        HttpEntity<String> httpEntity = new HttpEntity<>(headers);
        // Make the request
		ResponseEntity<GoogleUserInfoResponse> response = restTemplate.exchange(googleUserInfoEndpoint,HttpMethod.GET,httpEntity,GoogleUserInfoResponse.class);
		if (response.getStatusCode() != HttpStatusCode.valueOf(200)){
			throw new AppException("Can't sign with Google", HttpStatus.UNAUTHORIZED);
		}
		GoogleUserInfoResponse userResponse = response.getBody();
		System.out.println(response.getBody().toString());
		
		Set<Role> roles = new HashSet<>();
		roles.add(roleRepository.findByAuthority("ROLE_USER"));
		if (userRepository.existsByEmail(userResponse.getEmail())) {
			User user = userRepository.findByEmail(userResponse.getEmail());
			return getUserLoginResponse(user);

		} else {
			User user = User.builder()
					.email(userResponse.getEmail())
					.firstName(userResponse.getGivenName())
					.lastName(userResponse.getFamilyName())
					.username(userResponse.getEmail())
					.profilePicture(userResponse.getPicture())
					.provider(Provider.GOOGLE)
					.roles(roles)
					.password("")
					.isEnabled(userResponse.isEmailVerified())
					.build();
			userRepository.save(user);		
			return getUserLoginResponse(user);
		}
	}

}