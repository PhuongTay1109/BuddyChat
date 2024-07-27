package com.buddy.chat.controller;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.buddy.chat.dto.response.ApiResponse;
import com.buddy.chat.service.CloudinaryService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/upload")
@RequiredArgsConstructor
public class UploadControllerV1 {
    private final CloudinaryService cloudinaryService;
    
    @PostMapping("/user-image")
    public ResponseEntity<ApiResponse> uploadUserImage(@Valid @RequestParam("image") MultipartFile file, @Valid @RequestParam("userId") Integer userId) {
        String url = cloudinaryService.uploadUserImage(userId, file);
        ApiResponse respponse = ApiResponse.builder()
    			.timestamp(LocalDateTime.now())
    			.message("Image uploaded")
    			.statusCode(HttpStatus.OK.value())
    			.data(url)
    			.build();
        return new ResponseEntity<>(respponse, HttpStatus.OK);
    }

}
