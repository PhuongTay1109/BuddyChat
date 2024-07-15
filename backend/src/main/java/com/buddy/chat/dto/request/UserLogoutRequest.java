package com.buddy.chat.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserLogoutRequest {
    @NotEmpty(message = "Refresh token must not be empty")
    @NotNull(message = "Refresh token must not be empty")
    private String refreshToken;
}