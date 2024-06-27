package simple.blog.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


// Use for auto-generate Id of each document
@Document(collection = "database_sequences")
@Data
public class DatabaseSequence {
    @Id
    private String id; // SEQUENCE_NAME

    private long seq;
}
