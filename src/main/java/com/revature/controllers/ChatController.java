package com.revature.controllers;

import com.revature.models.ChatMessage;
import com.revature.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Controller
@RestController
@CrossOrigin(origins = {"http://localhost:4200","http://52.37.182.192:4200"}, allowCredentials = "true")
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    private ChatService chatService;

    @MessageMapping("/message/{roomName}") // app/message
    @SendTo("/chatroom/{roomName}") // chatroom
    private ChatMessage receivePublicMessage(@Payload ChatMessage message , @DestinationVariable String roomName) {
        chatService.addChatRoom(roomName);
        return message;
    }

    @GetMapping("/chatrooms")
    private Set<String> getChatRooms() {
        return chatService.getChatRooms();
    }

    @SubscribeMapping("/{roomName}")
    private Set<String> subscribeToChatRoom(@DestinationVariable String roomName) {
        System.out.println("Subscribing to chatroom: " + roomName);
        chatService.addChatRoom(roomName);
        return chatService.getChatRooms();
    }
}
