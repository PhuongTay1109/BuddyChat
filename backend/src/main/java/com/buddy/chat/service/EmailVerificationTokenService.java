package com.buddy.chat.service;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.CompletableFuture;

import com.buddy.chat.model.EmailVerificationToken;

import jakarta.mail.MessagingException;

public interface EmailVerificationTokenService {
	
	public CompletableFuture<Void> sendEmailConfirmation(String recipientEmail) throws UnsupportedEncodingException, MessagingException;
	
	public boolean confirmAccountRegistration(String token) throws UnsupportedEncodingException, MessagingException;
	
	public boolean isTokenExpired(EmailVerificationToken token);
}