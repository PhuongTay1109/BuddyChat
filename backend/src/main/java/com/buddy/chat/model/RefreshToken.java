package com.buddy.chat.model;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Document("refresh_token")
public class RefreshToken {
    private String username;
    private String token;
}
