package com.buddy.chat.service;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryService {
	public String uploadUserImage(Integer userId, MultipartFile file);
}
