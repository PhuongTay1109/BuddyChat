package com.buddy.chat.mapper;

import java.util.LinkedHashSet;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.buddy.chat.dto.response.UserResponse;
import com.buddy.chat.model.Role;
import com.buddy.chat.model.User;

@Component
public class UserMapper {

   // User toUser(UserRegistrationDTO request);

   public static UserResponse toUserResponse(User user) {
      if (user == null) {
         return null;
      }
      UserResponse.UserResponseBuilder userResponse = UserResponse.builder();
      userResponse.email(user.getEmail());
      userResponse.firstName(user.getFirstName());
      userResponse.isEnabled(user.getIsEnabled());
      userResponse.lastName(user.getLastName());
      userResponse.profilePicture(user.getProfilePicture());
      Set<Role> set = user.getRoles();
      if (set != null) {
         userResponse.roles(new LinkedHashSet<Role>(set));
      }
      userResponse.status(user.getStatus());
      userResponse.userId(user.getUserId());
      userResponse.username(user.getUsername());

      return userResponse.build();

   }

   // public static User toModelFromCreationRequest(UserUpdateRequest userUpdateRequest) {
   //    if (userUpdateRequest == null) {
   //       return null;
   //    } 
   // }
}