package com.buddy.chat.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.buddy.chat.enums.Provider;
import com.buddy.chat.enums.Status;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document("users")
@Getter
@Setter
@Builder
public class User implements UserDetails  {
    private static final long serialVersionUID = 1L;

	@Transient //this field is not persisted in the database
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Integer userId;

    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String profilePicture;
    private Boolean isEnabled;
    private Provider provider;

    private Status status;
    

    @DBRef
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}