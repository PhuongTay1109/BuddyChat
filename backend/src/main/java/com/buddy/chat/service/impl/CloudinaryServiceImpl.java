package com.buddy.chat.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.buddy.chat.exception.AppException;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.CloudinaryService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryServiceImpl implements CloudinaryService {
	private final Cloudinary cloudinary;
	private final UserRepository userRepository;

	@Override
	public String uploadUserImage( Integer userId, MultipartFile file) {
		if (file == null) {
			throw new RuntimeException("Image upload fail");
		}

		User existingUser = userRepository.findByUserId(userId);
		if (existingUser == null) {
			throw new AppException("User not found with ID " + userId, HttpStatus.NOT_FOUND);
		}

		String oldAvatarUrl = existingUser.getProfilePicture();
		String oldPublicId = extractPublicIdFromUrl(oldAvatarUrl);
		
		// delete old avt
		if (oldPublicId != null) {
            deleteImage(oldPublicId);
        }

		try {
			HashMap<Object, Object> options = new HashMap<>();
			Map<?, ?> uploadedFile = cloudinary.uploader().upload(file.getBytes(), options);
			// System.out.println(uploadedFile.toString());
			
			String publicId = (String) uploadedFile.get("public_id");
			String newAvtUrl = cloudinary.url().secure(true).generate(publicId);
			// System.out.println("newAvtUrl " + newAvtUrl);
			
			existingUser.setProfilePicture(newAvtUrl);
			userRepository.save(existingUser);
			
			return newAvtUrl;
		} catch (IOException e) {
			throw new AppException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	private void deleteImage(String publicId) {
	    try {
	        cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	private String extractPublicIdFromUrl(String url) {
		if (url == null || url.isEmpty()) {
			return null;
		}

		// Split the URL by '/'
		String[] parts = url.split("/");

		// The `public_id` is the last segment in the URL path
		String publicId = parts[parts.length - 1];

		return publicId;
	}
}
