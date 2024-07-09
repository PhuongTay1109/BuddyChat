package com.buddy.chat.dto.response;

import java.util.Set;

import com.buddy.chat.enums.Status;
import com.buddy.chat.model.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponse {
    private Integer userId;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private Set<Role> roles;
    private Boolean isEnabled;
    private Status status;
}
