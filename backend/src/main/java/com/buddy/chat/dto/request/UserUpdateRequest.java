package com.buddy.chat.dto.request;

import com.buddy.chat.model.User;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserUpdateRequest {
    @NotBlank(message = "Username must be not blank!")
    @NotNull(message = "Username must be not empty!")
    private String username;

    @NotBlank(message = "First name must be not blank!")
    @NotNull(message = "Username must be not empty!")
    @Pattern(regexp = User.VIETNAMESE_NAME_REGEX, message = "First letter  of first name must be capitalized!")
    private String firstName;

    @NotBlank(message = "Last name must be not blank!")
    @NotNull(message = "Username must be not empty!")
    @Pattern(regexp = User.VIETNAMESE_NAME_REGEX, message = "First letter of last name must be capitalized!")
    private String lastName;
}