package com.chat.controller;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

import com.chat.model.ChatMessage;

@Controller
public class ChatController {

	 @MessageMapping("/chat.sendMessage")  //
	    @SendTo("/topic/public") // subscribed user to send msg
	    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
	        return chatMessage;
	    }

	    @MessageMapping("/chat.addUser")
	    @SendTo("/topic/public")
	    public ChatMessage addUser(@Payload ChatMessage chatMessage,
	                               SimpMessageHeaderAccessor headerAccessor) {
	        // Add username to WebSocket session
	        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
	        return chatMessage;
	    }
	    
	    
	   
}
