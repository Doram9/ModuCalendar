package kopo.poly.persistance.redis.impl;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.dto.VoteInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.redis.IAppoMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("AppoMapper")
public class AppoMapper extends AbstractMongoDBComon implements IAppoMapper {

    public final RedisTemplate<String, Object> redisDB;

    public AppoMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;


    }

    @Override
    public int createAppo(AppoInfoDTO pDTO, UserInfoDTO uDTO) throws Exception {

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        String redisKey = pDTO.getAppoCode();
        String userId = uDTO.getUserId();
        String userName = uDTO.getUserName();

        pDTO.setFirdate("");
        pDTO.setSecdate("");
        pDTO.setThidate("");

        VoteInfoDTO vDTO = new VoteInfoDTO();
        vDTO.setUserid(userId);
        vDTO.setUsername(userName);
        vDTO.setVotetf(false);
        vDTO.setPosday(null);
        vDTO.setNegday(null);

        List<VoteInfoDTO> rList = new ArrayList<>();
        rList.add(vDTO);

        pDTO.setUserlist(rList);

        redisDB.opsForList().rightPush(redisKey, pDTO);

        redisDB.expire(redisKey, 31, TimeUnit.DAYS);


        //가져올 컬렉션 선택
        MongoCollection<Document> rCol = mongodb.getCollection("User");


        //userDB에 약속방 추가
        Document query = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();
        String roomcode = pDTO.getTitle() + "*_*" + redisKey;

        updateQuery.append("appoList", roomcode);

        UpdateResult updateResults = rCol.updateOne(query, new Document("$push", updateQuery));
        int res = (int) updateResults.getMatchedCount();


        pDTO = null;

        return res;
    }

    @Override
    public void deleteAppo(HashMap<String, Object> pMap) throws Exception {

    }

    @Override
    public AppoInfoDTO getAppoInfo(String code) throws Exception {

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        AppoInfoDTO rDTO = null;
        if(redisDB.hasKey(code)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(code, 0, -1);
            rDTO = rList.get(0);

            return rDTO;
        } else {

            return null;
        }

    }

    @Override
    public HashMap<String, ArrayList<String>> getVoteInfo(Map<String, Object> pMap) throws Exception {
        log.info("mapper.getVoteInfo start");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        String userid = (String) pMap.get("userId");
        String appoCode = (String) pMap.get("appoCode");
        List<String> posdays = (List) pMap.get("posdays");
        List<String> negdays = (List) pMap.get("negdays");


        ArrayList<String> rPosdays = new ArrayList<>();

        ArrayList<String> rNegdays = new ArrayList<>();

        rPosdays.addAll(posdays);
        rNegdays.addAll(negdays);

        AppoInfoDTO pDTO = null;


        if(redisDB.hasKey(appoCode)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(appoCode, 0, -1);
            pDTO = rList.get(0); //정보를 pDTO에 저장
            Iterator<VoteInfoDTO> pList = pDTO.getUserlist().iterator(); //전체 정보 속 투표 정보들 가져오기
            List<VoteInfoDTO> nList = new ArrayList<>(); //새로운 투표정보
            while(pList.hasNext()) {
                VoteInfoDTO vDTO = pList.next(); //투표정보리스트들 중 하나
                String chgid = vDTO.getUserid(); //바꿀 아이디

                if(chgid.equals(userid)) { //발견하면
                    vDTO.setVotetf(true);
                    vDTO.setPosday(posdays);
                    vDTO.setNegday(negdays);
                    nList.add(vDTO);
                } else { //다른 유저일경우
                    nList.add(vDTO);
                    if(vDTO.isVotetf()) { //투표를 했으면
                        rPosdays.addAll(vDTO.getPosday());
                        rNegdays.addAll(vDTO.getNegday());
                    }
                }
            }
            pDTO.setUserlist(nList);

            redisDB.opsForList().set(appoCode, 0, pDTO); //투표정보 수정

            HashMap<String, ArrayList<String>> rMap = new HashMap<>();
            rMap.put("posdays", rPosdays);
            rMap.put("negdays", rNegdays);

            return rMap;
        } else {
            return null;
        }
    }

    @Override
    public void updateResult(Map<String, Object> pMap) throws Exception {

        log.info("mapper.updateResult start");

        String roomcode = (String) pMap.get("roomcode");
        String firday = (String) pMap.get("firday");
        String secday = (String) pMap.get("secday");
        String thiday = (String) pMap.get("thiday");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        AppoInfoDTO pDTO = null;
        if(redisDB.hasKey(roomcode)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(roomcode, 0, -1);
            pDTO = rList.get(0); //정보를 pDTO에 저장
            pDTO.setFirdate(firday);
            pDTO.setSecdate(secday);
            pDTO.setThidate(thiday);
            redisDB.opsForList().set(roomcode, 0, pDTO); //투표정보 수정
        }
    }
}
