package com.example.Healthcare_app.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.Healthcare_app.dto.ChatMessageRequest;
import com.example.Healthcare_app.entity.ChatMessage;
import com.example.Healthcare_app.entity.User;
import com.example.Healthcare_app.repository.ChatMessageRepository;
import com.example.Healthcare_app.repository.UserRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ChatService {
	
	private final RestTemplate restTemplate = new RestTemplate();
	
	@Value("${ollama.url}")
    private  String ollamaUrl;

    @Autowired
    private  ChatMessageRepository chatMessageRepository;
    
    private  UserRepository userRepository;
    
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    
    
     public ChatService(ChatMessageRepository chatMessageRepository,UserRepository userRepository) {
	  this.userRepository = userRepository;
	  this.chatMessageRepository=chatMessageRepository;
	}

    public ChatMessage processMessage(ChatMessageRequest message) {
    	
    	User user = userRepository.findById(message.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + message.getUserId()));

        String prompt = "You are a healthcare chatbot. Answer this patient query concisely: " + message.getMessage();
        String jsonRequest = String.format("{\"model\": \"tinyllama\", \"prompt\": \"%s\", \"stream\": false}", prompt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(jsonRequest, headers);

        String responseStr = restTemplate.postForObject(ollamaUrl, requestEntity, String.class);
        String chatbotReply = "Sorry, I couldn't process your message.";

        try {
            JsonNode root = OBJECT_MAPPER.readTree(responseStr);
            chatbotReply = root.path("response").asText();
        } catch (Exception e) {
            // log error
        }

        ChatMessage chatMessage = ChatMessage.builder()
                .user(user)
                .message(message.getMessage())
                .response(chatbotReply)
                .timestamp(LocalDateTime.now())
                .build();

        return chatMessageRepository.save(chatMessage);
    }

}
