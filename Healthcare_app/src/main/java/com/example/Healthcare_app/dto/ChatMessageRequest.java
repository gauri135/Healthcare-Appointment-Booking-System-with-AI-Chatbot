package com.example.Healthcare_app.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ChatMessageRequest {

	private Long userId;
    private String message;
    private LocalDateTime timestamp;
}
