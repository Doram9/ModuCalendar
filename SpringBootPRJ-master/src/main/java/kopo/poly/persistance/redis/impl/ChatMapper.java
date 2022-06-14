package kopo.poly.persistance.redis.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.*;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.redis.IChatMapper;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Slf4j
@Component("ChatMapper")
public class ChatMapper extends AbstractMongoDBComon implements IChatMapper {

    public final RedisTemplate<String, Object> redisDB;

    public ChatMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }


    @Override
    public void addMessage(ChatMessageDTO cDTO) throws Exception {

        log.info("mapper.addMessage start");

        String redisKey = cDTO.getPrjCode();
        log.info("redisKey : " + redisKey);

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDTO.class));

        redisDB.opsForList().rightPush(redisKey, cDTO);

        redisDB.expire(redisKey, 5, TimeUnit.HOURS);

    }

    @Override
    public void saveMessageList(String prjCode) throws Exception {
        log.info("mapper.saveMessageList start");
        log.info("redisKey : " + prjCode);
        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDTO.class));
        List<ChatMessageDTO> rChatLog = null;
        if(redisDB.hasKey(prjCode)) {
            rChatLog = (List) redisDB.opsForList().range(prjCode, 0, -1);
            redisDB.delete(prjCode);
        }
        log.info("채팅 개수 : " + rChatLog.size());

        String colNm = "Chat";

        super.createCollection(colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("prjCode", prjCode);

        Document projection = new Document();
        projection.append("_id", 0);

        Document rs = col.find(query).projection(projection).first();
        if(rs == null) {
            log.info("데이터 없음");
            ChatLogDTO logDTO = new ChatLogDTO();
            logDTO.setPrjCode(prjCode);
            logDTO.setChatLog(rChatLog);
            col.insertOne(new Document(new ObjectMapper().convertValue(logDTO, Map.class)));
            logDTO = null;
        } else {
            log.info("데이터 있음");
            for(ChatMessageDTO cDTO : rChatLog) {

                ObjectMapper mapper = new ObjectMapper();
                Document dto = mapper.convertValue(cDTO, Document.class);

                Document updateQuery = new Document();
                updateQuery.append("chatLog", dto);

                col.updateOne(query, new Document("$push", updateQuery));
            }
        }


    }

    @Override
    public List<ChatMessageDTO> getChatLog(String prjCode) throws Exception {
        log.info("mapper.getChatLog start");
        log.info("redisKey : " + prjCode);

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(ChatMessageDTO.class));

        if(redisDB.hasKey(prjCode)) {
            List<ChatMessageDTO> rList = (List) redisDB.opsForList().range(prjCode, 0, -1);

            return rList;
        } else {
            return null;
        }
    }
}
