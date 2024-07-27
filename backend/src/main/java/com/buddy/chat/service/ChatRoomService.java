package com.buddy.chat.service;

import java.util.List;

import com.buddy.chat.model.ChatRoom;
import com.buddy.chat.model.User;

public interface ChatRoomService {
	ChatRoom createChatRoom(List<Integer> userIds);
	List<User> findUsersByChatRoom(String chatRoomId);
}
