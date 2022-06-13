package kopo.poly.persistance.redis;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.ChatMessageDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.List;

public interface IChatMapper {

    void addMessage(ChatMessageDTO cDTO) throws Exception;

    void saveMessageList(String prjCode) throws Exception;

}
