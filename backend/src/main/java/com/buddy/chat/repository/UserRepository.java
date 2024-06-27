package com.buddy.chat.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.buddy.chat.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
    public User findByUsername(String username);
    public User findByUserId(Integer userId);
}
