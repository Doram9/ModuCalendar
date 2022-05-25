package kopo.poly.persistance.redis.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.redis.IAppoMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("AppoMapper")
public class AppoMapper extends AbstractMongoDBComon implements IAppoMapper {

    public final RedisTemplate<String, Object> redisDB;

    public AppoMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;


    }

    @Override
    public int createAppo(AppoInfoDTO pDTO, String userid) throws Exception {

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        String redisKey = pDTO.getAppoCode();

        redisDB.opsForList().rightPush(redisKey, pDTO);

        redisDB.expire(redisKey, 31, TimeUnit.DAYS);


        //가져올 컬렉션 선택
        MongoCollection<Document> rCol = mongodb.getCollection("User");


        //쿼리 만들기
        Document query = new Document();
        query.append("userId", userid);

        Document updateQuery = new Document();
        String roomcode = pDTO.getTitle() + "*" + redisKey;

        updateQuery.append("appList", roomcode);

        UpdateResult updateResults = rCol.updateOne(query, new Document("$push", updateQuery));
        int res = (int) updateResults.getMatchedCount();


        pDTO = null;

        return res;
    }

    @Override
    public void deleteAppo(HashMap<String, Object> pMap) throws Exception {

    }

    @Override
    public AppoInfoDTO getAppoInfo(String roomcode) throws Exception {
        return null;
    }
}
