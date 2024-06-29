package com.buddy.chat.model;

import java.util.Calendar;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document("email_verification_tokens")
public class EmailVerificationToken {


	private String userEmail;
	private String token;
	private Date expiryDate;

	public EmailVerificationToken(String userEmail, String token) {
		this.userEmail = userEmail;
		this.token = token;
		this.expiryDate = calculateExpiryDate(3);
	}

	private Date calculateExpiryDate(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, days);
		return calendar.getTime();
	}

}