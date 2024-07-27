package com.buddy.chat.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.buddy.chat.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, Integer> {
	
    public User findByUsername(String username);
    public User findByUserId(Integer userId);
    public User findByEmail(String email);
 
    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
	public List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String query, String query2);
}