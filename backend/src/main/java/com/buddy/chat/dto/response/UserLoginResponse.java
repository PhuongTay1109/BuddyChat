package com.buddy.chat.dto.response;

import java.util.Set;

import com.buddy.chat.model.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserLoginResponse {
    private String accessToken;
    private String refreshToken;
    private Set<Role> roles;
}
