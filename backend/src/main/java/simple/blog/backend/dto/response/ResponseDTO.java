package simple.blog.backend.dto.response;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDTO {

    LocalDateTime timestamp;
    String message;
    Integer statusCode;
    Object data;
}
