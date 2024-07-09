package com.buddy.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class OAuthTokenRequest {
    @NotNull(message = "Token must not be empty")
    @NotBlank(message = "Token must not be empty")
    public String token;
}
