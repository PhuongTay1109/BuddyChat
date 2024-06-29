package com.buddy.chat.mapper;

import org.mapstruct.Mapper;

import com.buddy.chat.dto.response.UserResponseDTO;
import com.buddy.chat.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
   //User toUser(UserRegistrationDTO request);
	
	UserResponseDTO toUserResponse(User user);

}
