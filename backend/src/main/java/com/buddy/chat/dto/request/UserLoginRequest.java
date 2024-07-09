package com.buddy.chat.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class UserLoginRequest {

    @NotBlank(message =  "Username must be not blank!")
    @NotNull(message =  "Username must be not empty!")
    private String username;
    @Size(min = 5, max = 15, message = "Password must be from 5 to 15 letters!")
    private String password;
    
    @Override
    public String toString() {
        return "UserLoginRequest [username=" + username + ", password=" + password + "]";
    }
    

}