package kopo.poly.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import kopo.poly.dto.ChatMessageDTO;
import kopo.poly.persistance.mongodb.IEventMapper;
import kopo.poly.persistance.redis.IChatMapper;
import kopo.poly.service.IChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

@Slf4j
@Service("ChatService")
public class ChatService implements IChatService {

    @Resource(name="ChatMapper")
    IChatMapper chatMapper;

    @Override
    public void addMessage(ChatMessageDTO cDTO) throws Exception {
        log.info("service.addMessage start");

        chatMapper.addMessage(cDTO);
    }

    @Override
    public void saveMessage(ChatMessageDTO cDTO) throws Exception {
        log.info("service.saveMessage start");

        //redis db에 prjCode가 redisKey 인 List<String> 데이터를 가져오기
        String prjCode = cDTO.getPrjCode();
        log.info("saveMessage.redisKey : " + prjCode);
        chatMapper.saveMessageList(prjCode);

    }

    @Override
    public List<ChatMessageDTO> getChatLog(String prjCode) throws Exception {

        return chatMapper.getChatLog(prjCode);
    }

}
