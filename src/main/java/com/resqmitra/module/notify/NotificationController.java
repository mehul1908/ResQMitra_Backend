package com.resqmitra.module.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;

import com.resqmitra.module.notify.service.NotificationService;

@Controller
public class NotificationController {

	@Autowired
    private NotificationService service;


    // Client sends to: /app/notify
    @MessageMapping("/notify")
    public void sendToSelf(String message) {
        // Send back only to the logged-in user
        service.notifyUser("mehul@gmail.com", "Private: " + message);
    }
}


