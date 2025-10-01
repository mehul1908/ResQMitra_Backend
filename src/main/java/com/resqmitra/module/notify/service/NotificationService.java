package com.resqmitra.module.notify.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.resqmitra.module.notify.dto.NotificationPayload;
@Service
public class NotificationService {

	
	@Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyUser(String username, String message) {
        // Sends a private message to that user’s queue
    	System.out.println("--------------\n"+message);
        messagingTemplate.convertAndSendToUser(
                "mehul123",
                "/queue/notification", // destination
                message
        );
    }
}


