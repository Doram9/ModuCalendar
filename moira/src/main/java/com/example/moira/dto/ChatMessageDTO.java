package com.example.moira.dto;

import lombok.Data;

@Data
public class ChatMessageDTO {
    private String prjCode;
    private String userId;
    private String userName;
    private String message;
    private String sendTime;
}
