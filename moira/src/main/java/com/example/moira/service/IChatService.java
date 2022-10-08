package com.example.moira.service;

import com.example.moira.dto.ChatMessageDTO;

import java.util.List;

public interface IChatService {

    void addMessage(ChatMessageDTO cDTO) throws Exception;

    void saveMessage(ChatMessageDTO cDTO) throws Exception;

    List<ChatMessageDTO> getChatLog(String prjCode) throws Exception;
}
