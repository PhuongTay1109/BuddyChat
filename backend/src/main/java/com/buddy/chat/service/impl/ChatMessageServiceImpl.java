package com.buddy.chat.service.impl;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.buddy.chat.model.ChatMessage;
import com.buddy.chat.repository.ChatMessageRepository;
import com.buddy.chat.service.ChatMessageService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    @Override
    public List<ChatMessage> findChatMessagesByChatRoom(String chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId, Sort.by(Sort.Order.by("timestamp")));
    }
    @Override
    public List<ChatMessage> findChatMessagesByChatRoomIdAndSenderId(String chatRoomId, Integer senderId, Sort sort) {
        // TODO Auto-generated method stub
        return chatMessageRepository.findByChatRoomIdAndSenderId(chatRoomId, senderId, Sort.by(Sort.Order.by("timestamp")));
    }

}
