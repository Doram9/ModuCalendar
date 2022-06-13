package kopo.poly.service;

import kopo.poly.dto.ChatMessageDTO;

public interface IChatService {

    int addMessage(ChatMessageDTO cDTO) throws Exception;

    int saveMessage(ChatMessageDTO cDTO) throws Exception;
}
