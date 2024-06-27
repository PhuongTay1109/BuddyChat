package simple.blog.backend.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import simple.blog.backend.dto.response.ResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseDTO> handleUnwantedException(Exception e) {
        ResponseDTO responseDTO = new ResponseDTO(null, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now());
        return new ResponseEntity<>(responseDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(AppException.class)
    public ResponseEntity<ResponseDTO> handleAppException(AppException e) {
        String message = e.getMessage();
        HttpStatus httpStatus = e.getHttpStatus();
        ResponseDTO responseDTO = new ResponseDTO(null, message, httpStatus.value() , message);
        return new ResponseEntity<>(responseDTO, httpStatus);
    }
}
