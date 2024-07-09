package com.buddy.chat.mapper;

import org.mapstruct.Mapper;

import com.buddy.chat.dto.response.UserResponse;
import com.buddy.chat.model.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
	
   //User toUser(UserRegistrationDTO request);
	
	UserResponse toUserResponse(User user);

}
