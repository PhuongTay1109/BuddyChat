package com.buddy.chat.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLoginResponse {
    private UserResponse user;
    private String accessToken;
    private String refreshToken;

}
