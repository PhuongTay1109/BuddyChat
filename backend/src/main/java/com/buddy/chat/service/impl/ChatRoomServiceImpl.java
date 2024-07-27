package com.buddy.chat.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.buddy.chat.exception.AppException;
import com.buddy.chat.model.ChatRoom;
import com.buddy.chat.model.User;
import com.buddy.chat.repository.ChatRoomRepository;
import com.buddy.chat.repository.UserRepository;
import com.buddy.chat.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public ChatRoom createChatRoom(List<Integer> userIds) {
        for (Integer userId : userIds) {
            if (!userRepository.existsById(userId)) {
                throw new AppException("User not found with ID " + userId, HttpStatus.NOT_FOUND);
            }
        }
        String chatRoomId = UUID.randomUUID().toString();
        ChatRoom newChatRoom = ChatRoom.builder()
        						.chatRoomId(chatRoomId)
        						.userIds(userIds)
        						.chatMessageIds(null)
        						.build();

        chatRoomRepository.save(newChatRoom);

        return newChatRoom;
    }

    @Override
    public List<User> findUsersByChatRoom(String chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new AppException("Chat room not found with ID " + chatRoomId, HttpStatus.NOT_FOUND));
        List<Integer> userIds = chatRoom.getUserIds();
        List<User> users = new ArrayList<>();
        for (Integer userId : userIds) {
            User user=  userRepository.findByUserId(userId);
            if (user == null) {
                throw new AppException("User not found with ID " + userId, HttpStatus.NOT_FOUND);
            }
            users.add(user);   
        }
        return users;
    }

}
