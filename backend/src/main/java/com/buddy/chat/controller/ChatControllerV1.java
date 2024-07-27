package com.buddy.chat.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatControllerV1 {
    private final SimpMessagingTemplate simpMessagingTemplate;
    
}
