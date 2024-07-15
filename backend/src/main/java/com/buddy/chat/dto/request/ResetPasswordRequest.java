package com.buddy.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ResetPasswordRequest {
	@NotNull(message = "Token must not be empty")
    @NotBlank(message = "Token must not be empty")
	private String token;
	
	@NotNull(message = "Password must not be empty")
    @NotBlank(message = "Password must not be empty")
	@Size(min = 5, max = 15, message = "Password must be from 5 to 15 letters!")
	private String password;
}	