package com.buddy.chat.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.buddy.chat.enums.Provider;
import com.buddy.chat.exception.AppException;
import com.buddy.chat.model.EmailVerificationToken;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.EmailVerificationTokenRepository;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.EmailVerificationTokenService;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailVerificationTokenServiceImpl implements EmailVerificationTokenService {

	private final JavaMailSender javaMailSender;
	private final EmailVerificationTokenRepository emailVerificationTokenRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Async
	@Override
	public CompletableFuture<Void> sendEmailConfirmation(String recipientEmail) {
		String token = UUID.randomUUID().toString();

		EmailVerificationToken emailVerificationToken = new EmailVerificationToken(recipientEmail, token);
		emailVerificationTokenRepository.save(emailVerificationToken);

		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		
		try {
			helper.setFrom("contact@simpleblog.com", "Simple Blog Support");
			helper.setTo(recipientEmail);

			String subject = "Please confirm your account";
			String link = "http://localhost:3000/confirm-account?token=" + token;

			String content = "<p>Hello,</p>" + 
					"<p>Thank you for registering with Simple Blog.</p>"
					+ "<p>Please click the link below to confirm your account:</p>" + 
					"<p><a href=\"" + link + "\">Confirm my account</a></p>" + "<br>"
					+ "<p>If you did not register for this account, please ignore this email.</p>";

			helper.setSubject(subject);
			helper.setText(content, true);

		} catch (MessagingException | UnsupportedEncodingException e) {
	        e.printStackTrace();
	        throw new AppException("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
	    }


		javaMailSender.send(message);
		return CompletableFuture.completedFuture(null);
	}

	@Override
	public boolean isTokenExpired(EmailVerificationToken token) {

		Date expiryDate = token.getExpiryDate();
		Date currentDate = new Date();

		return currentDate.after(expiryDate);
	}

	@Override
	public boolean confirmAccountRegistration(String token) {

		EmailVerificationToken emailToken = emailVerificationTokenRepository.findByToken(token);

		if (emailToken == null) {
			throw new AppException("Email token not found", HttpStatus.NOT_FOUND);
		}

		User user = userRepository.findByEmail(emailToken.getUserEmail());
		if (user == null) {
			throw new AppException("User not found", HttpStatus.NOT_FOUND);
		}

		// if the token has not expired
		if (!isTokenExpired(emailToken)) {
			// activate user account
			user.setIsEnabled(true);
			userRepository.save(user);

			// Delete token from the database
			emailVerificationTokenRepository.deleteByUserEmail(user.getEmail());
			return true;
		} else {
			// delete old token
			emailVerificationTokenRepository.deleteByUserEmail(user.getEmail());
			// send new email
			sendEmailConfirmation(user.getEmail());
			return false;
		}
	}

	@Override
	public User checkPasswordResetEmail(String email) {
		User user = userRepository.findByEmail(email);
		if (user == null) {
			throw new AppException("User not found", HttpStatus.NOT_FOUND);
		}
		
		if(user.getProvider() == Provider.FACEBOOK || user.getProvider() == Provider.GOOGLE) {
			throw new AppException("You have logged in Simple Blog with FACEBOOK or GOOGLE", HttpStatus.NOT_FOUND);
		}
		
		return user;
	}

	@Async
	@Override
	public CompletableFuture<Void> sendPasswordResetEmail(User user) {
		
		String token = UUID.randomUUID().toString();
		
		EmailVerificationToken emailVerificationToken = new EmailVerificationToken(user.getEmail(), token);
		emailVerificationTokenRepository.save(emailVerificationToken);
		
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setFrom("contact@simpleblog.com", "Simple Blog Support");
			helper.setTo(user.getEmail());

			String subject = "Reset Your Password";
			String link = "http://localhost:3000/reset-password?token=" + token;

			String content = "<p>Hello,</p>" 
					+ "<p>We received a request to reset your password.</p>"
					+ "<p>Please click the link below to reset your password:</p>" 
					+ "<p><a href=\"" + link + "\">Reset my password</a></p>" + "<br>"
					+ "<p>If you did not request a password reset, please ignore this email.</p>";

			helper.setSubject(subject);
			helper.setText(content, true);

		} catch (MessagingException | UnsupportedEncodingException e) {
	        e.printStackTrace();
	        throw new AppException("Failed to send email", HttpStatus.INTERNAL_SERVER_ERROR);
	    }

		javaMailSender.send(message);
		return CompletableFuture.completedFuture(null);
	}
	
	@Override
	public boolean resetPassword(String token, String password) {

		EmailVerificationToken emailToken = emailVerificationTokenRepository.findByToken(token);
		
		if (emailToken == null) {
			throw new AppException("Reset password link is not valid", HttpStatus.GONE);
		}
		
		User user = userRepository.findByEmail(emailToken.getUserEmail());
		if (user == null) {
			throw new AppException("User not found", HttpStatus.NOT_FOUND);
		}

		// if the token has not expired
		if (!isTokenExpired(emailToken)) {
			// change password
			user.setPassword(passwordEncoder.encode(password));
			userRepository.save(user);

			// Delete token from the database
			emailVerificationTokenRepository.deleteByUserEmail(user.getEmail());
			return true;
		} else {
			// delete old token
			emailVerificationTokenRepository.deleteByUserEmail(user.getEmail());
			// send new email
			sendPasswordResetEmail(user);
			return false;
		}
	}
}