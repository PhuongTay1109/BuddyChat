package com.buddy.chat.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserRegistrationDTO {
    
    @NotBlank(message =  "Username must be not blank!")
    @NotNull(message =  "Username must be not empty!")
    private String username;
    @Email(message = "Invalid email format!")
    private String email;

    @NotBlank(message =  "First name must be not blank!")
    @Pattern(regexp = "([A-Z][a-z]*)", message = "First letter  of first name must be capitalized!")
    private String firstName;
    
    @NotBlank(message =  "Last name must be not blank!")
    @Pattern(regexp = "([A-Z][a-z]*)", message = "First letter of last name must be capitalized!")
    private String lastName;

    @Size(min = 5, max = 10, message = "Password must be from 5 to 10 letters!")
    private String password;

    private String profilePicture;

}
