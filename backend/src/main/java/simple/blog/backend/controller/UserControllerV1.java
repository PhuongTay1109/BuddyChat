package simple.blog.backend.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import simple.blog.backend.dto.response.ResponseDTO;
import simple.blog.backend.model.User;
import simple.blog.backend.service.UserService;

@RequestMapping("/api/v1/users")
@RestController
public class UserControllerV1 {
    private UserService userService;

    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping()
    public ResponseEntity<ResponseDTO> getUserList() {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(), "Success", 200,userService.findAllUsers() );
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ResponseDTO> getUserByUserId(@PathVariable Integer userId) {
        ResponseDTO responseDTO = new ResponseDTO(LocalDateTime.now(),  "Success", 200, userService.findUserByUserId(userId));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK); 
    }
}
