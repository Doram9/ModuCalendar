package kopo.poly.persistance.redis;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.ChatMessageDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.List;

public interface IChatMapper {

    int addMessage(ChatMessageDTO cDTO) throws Exception;

    List<ChatMessageDTO> getMessageList(String prjCode) throws Exception;

}
