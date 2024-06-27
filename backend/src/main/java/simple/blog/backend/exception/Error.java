package simple.blog.backend.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Error {
    UNKNOW_EXCEPTION(500, "Uncategorized exception"),
    USER_EXISTED(409,"User existed"),
    USER_NOT_FOUND(404, "User not found");

    private int code;
    private String message;

}
