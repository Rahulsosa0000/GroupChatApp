package com.chat.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
    	registry.addEndpoint("/chat") // WebSocket endpoint
 		
        .setAllowedOrigins("*"); // Allow cross-origin requests

//    	registry.addEndpoint("/chat") // WebSocket endpoint
//         		
//                .setAllowedOrigins("*") // Allow cross-origin requests
//                .withSockJS(); // Enable SockJS fallback options
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/queue"); // server to client 
        config.setApplicationDestinationPrefixes("/app"); // (client -> server>
    }
}        
