package com.resqmitra.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        // Enables in-memory broker for broadcasting (/topic) and private queues (/queue)
        config.enableSimpleBroker("/topic", "/queue");

        // Prefix for client requests. Example: client sends to /app/notify
        config.setApplicationDestinationPrefixes("/app");

        // Prefix for user-specific messages (important for private notifications)
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // WebSocket endpoint where clients connect
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("http://127.0.0.1:5500", "http://localhost:3000" , "http://localhost:9090")
                .withSockJS(); // fallback for browsers that don't support WebSocket
    }
}

