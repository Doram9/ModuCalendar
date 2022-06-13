package kopo.poly.persistance.redis.impl;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.ChatMessageDTO;
import kopo.poly.persistance.redis.IChatMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component("ChatMapper")
public class ChatMapper implements IChatMapper {

    public final RedisTemplate<String, Object> redisDB;

    public ChatMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }


    @Override
    public int addMessage(ChatMessageDTO cDTO) throws Exception {

        log.info("mapper.addMessage start");

        String redisKey = cDTO.getPrjCode();
        log.info("redisKey : " + redisKey);

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDTO.class));

        redisDB.opsForList().rightPush(redisKey, cDTO);

        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

        int res = 1;


        return res;
    }

    @Override
    public List<ChatMessageDTO> getMessageList(String prjCode) throws Exception {
        log.info("mapper.getMessageList start");
        log.info("redisKey : " + prjCode);
        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDTO.class));
        List<ChatMessageDTO> rChatLog = null;
        if(redisDB.hasKey(prjCode)) {
            rChatLog = (List) redisDB.opsForList().range(prjCode, 0, -1);
            redisDB.delete(prjCode);
        }

        return rChatLog;
    }
}
