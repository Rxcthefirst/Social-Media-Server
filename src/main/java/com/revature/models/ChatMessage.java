package com.revature.models;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ChatMessage {
    private String senderName;
    private String roomName;
    private String message;
    private String date;
    private Status status;
}
