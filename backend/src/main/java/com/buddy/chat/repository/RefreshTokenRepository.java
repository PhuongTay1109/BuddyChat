package com.buddy.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.buddy.chat.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
    RefreshToken findByUsername(String username);
    RefreshToken findByToken(String token);
}
