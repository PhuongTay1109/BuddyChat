package com.buddy.chat.service;

import java.util.List;

import org.springframework.data.domain.Sort;

import com.buddy.chat.model.ChatMessage;

public interface ChatMessageService {
    List<ChatMessage> findChatMessagesByChatRoom(String chatRoomId);
    List<ChatMessage> findChatMessagesByChatRoomIdAndSenderId(String chatRoomId, Integer senderId, Sort sort);
}
