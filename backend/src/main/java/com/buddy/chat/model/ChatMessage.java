package com.buddy.chat.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("chat_messages")
public class ChatMessage {
	@Id
	private String chatMessageId;
	private String chatRoomId; // Reference to the chat room
	private Integer senderId;
	private Integer receiverId; // Can be omitted for group chats
	private String content;
	private Date timestamp; 
}