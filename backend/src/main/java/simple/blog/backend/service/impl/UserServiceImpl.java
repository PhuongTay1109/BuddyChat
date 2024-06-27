package simple.blog.backend.service.impl;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import simple.blog.backend.exception.AppException;
import simple.blog.backend.model.User;
import simple.blog.backend.repository.UserRepository;
import simple.blog.backend.service.UserService;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
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





}
