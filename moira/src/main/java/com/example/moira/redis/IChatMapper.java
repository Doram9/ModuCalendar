package com.example.moira.redis;

import kopo.poly.dto.ChatMessageDTO;

import java.util.List;

public interface IChatMapper {

    void addMessage(ChatMessageDTO cDTO) throws Exception;

    void saveMessageList(String prjCode) throws Exception;

    List<ChatMessageDTO> getChatLog(String prjCode) throws Exception;

}
