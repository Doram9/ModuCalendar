package kopo.poly.service;

import kopo.poly.dto.ChatMessageDTO;

public interface IChatService {

    void addMessage(ChatMessageDTO cDTO) throws Exception;

    void saveMessage(ChatMessageDTO cDTO) throws Exception;
}
