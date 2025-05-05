package com.example.Healthcare_app.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Healthcare_app.dto.ChatMessageRequest;
import com.example.Healthcare_app.entity.ChatMessage;
import com.example.Healthcare_app.service.ChatService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/chat")
@Slf4j
@Tag(name = "Chat Controller", description = "API to send and process chat messages")
public class ChatController {
	
	private ChatService chatService;
	
	public ChatController(ChatService chatService)
	{
		this.chatService = chatService;
	}
	
	@PostMapping
	@Operation(summary = "Send chat message")
	public ResponseEntity<ChatMessage> sendMessage(@RequestBody ChatMessageRequest message) {
		
		log.info("chatcontroller here....");
        return ResponseEntity.ok(chatService.processMessage(message));
    }

}
