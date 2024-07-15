package com.buddy.chat.service;

import java.util.concurrent.CompletableFuture;

import com.buddy.chat.model.EmailVerificationToken;

public interface EmailVerificationTokenService {
	
public CompletableFuture<Void> sendEmailConfirmation(String recipientEmail);
	
	public boolean confirmAccountRegistration(String token);
	
	public boolean isTokenExpired(EmailVerificationToken token);
	
	public CompletableFuture<Void> sendPasswordResetEmail(String username);
	
	public boolean resetPassword(String token, String password);
	
}