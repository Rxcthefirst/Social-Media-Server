package com.revature.models;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message <T> {
    private String message;
    private T entity;
}
