package com.buddy.chat.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.buddy.chat.model.EmailVerificationToken;

@Repository
public interface EmailVerificationTokenRepository extends MongoRepository<EmailVerificationToken, Integer> {

	public EmailVerificationToken findByToken(String token);
	
	void deleteByUserEmail(String userEmail);
}
