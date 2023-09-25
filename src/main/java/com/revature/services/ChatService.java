package com.revature.services;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ChatService {

    private Set<String> chatRooms = new HashSet<>(){{add("public");}};

    public Set<String> getChatRooms() {
        return chatRooms;
    }

    public void addChatRoom(String chatRoom) {
        chatRooms.add(chatRoom);
    }
}
