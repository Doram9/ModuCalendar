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
    public int addMessage(ChatMessageDTO cDTO) throws Exception {
        log.info("service.addMessage start");

        return chatMapper.addMessage(cDTO);
    }

    @Override
    public int saveMessage(ChatMessageDTO cDTO) throws Exception {
        log.info("service.saveMessage start");

        //redis db에 prjCode가 redisKey 인 List<String> 데이터를 가져오기
        String prjCode = cDTO.getPrjCode();
        log.info("saveMessage.redisKey : " + prjCode);
        List<ChatMessageDTO> chatLog = chatMapper.getMessageList(prjCode);

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr = mapper.writeValueAsString(chatLog);
        log.info(jsonStr);
        //파일에 덮어쓰기
        return 0;
    }
}
