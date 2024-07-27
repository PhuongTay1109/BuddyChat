package com.buddy.chat.model;

import java.util.List;

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
@Document("chat_rooms")
public class ChatRoom {
	 @Id
	 private String chatRoomId;
	 private List<Integer> userIds;
	 private List<String> chatMessageIds;

}
