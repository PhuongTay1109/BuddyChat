package com.buddy.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.buddy.chat.model.RefreshToken;

@Repository
public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {
	RefreshToken findByUsername(String username);
    RefreshToken findByToken(String token);

    // https://docs.spring.io/spring-data/data-mongo/docs/1.5.1.RELEASE/reference/html/mongo.repositories.html#mongodb.repositories.queries.delete
    Long deleteRefreshTokenByUsername(String username); // A numeric return type directly removes the matching documents returning the total number of documents removed.
    Long deleteRefreshTokenByToken(String token); // A numeric return type directly removes the matching documents returning the total number of documents removed.
}