package com.buddy.chat.dto.response;

import java.util.Set;

import com.buddy.chat.model.Role;

import lombok.Data;

@Data
public class UserResponseDTO {
    private Integer userId;
    private String username;
    private String email;
    private Set<Role> roles;

}
