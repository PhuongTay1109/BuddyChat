package com.buddy.chat.dto.request;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UploadUserImageRequest {
    @NotNull(message = "userId is not null")
    @NotBlank(message = "userId is not blank")
    private Integer userId;
    @NotNull(message = "File is not null")
    private MultipartFile file;
}