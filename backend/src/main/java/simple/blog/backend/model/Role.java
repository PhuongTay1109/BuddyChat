package simple.blog.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Document("roles")
@RequiredArgsConstructor // Add @NonNull
public class Role implements GrantedAuthority {

    @Transient //this field is not persisted in the database
    public static final String SEQUENCE_NAME = "roles_sequence";

    @Id
    @Field(name = "role_id")
    private Integer roleId;

    @NonNull
    private String authority;


    @Override
    public String getAuthority() {
        return authority;
    }
    
}
