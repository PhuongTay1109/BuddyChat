package com.buddy.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class RefreshTokenRequest {
	
	@NotNull(message = "Refresh token must be not null")
	@NotBlank(message = "Refresh token must be not blank")
	private String refreshToken;

}
