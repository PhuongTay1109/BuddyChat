package com.buddy.chat.dto.response;

import java.util.Set;

import com.buddy.chat.model.Role;
import com.buddy.chat.model.Status;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class UserResponseDTO {
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
