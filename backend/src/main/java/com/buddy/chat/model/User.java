package com.buddy.chat.model;

import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.mongodb.lang.NonNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Document("users")
@Getter
@Setter
@Builder
public class User extends AbstractEntity implements UserDetails  {
    @Transient //this field is not persisted in the database
    public static final String SEQUENCE_NAME = "users_sequence";

    @Id
    private Integer userId;

    @NonNull // For constructor
    private String username;
    @NonNull // For constructor
    private String password;
    @NonNull // For constructor
    private String email;
    @NonNull
    private String firstName;
    @NonNull
    private String lastName;
    @NonNull
    private String profilePicture;
    @NonNull
    private Boolean isEnabled;

    private Status status;
    

    @DBRef
    @NonNull // For constructor
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
