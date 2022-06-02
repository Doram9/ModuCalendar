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
    public int deleteAppo(HashMap<String, Object> pMap) throws Exception {

        String userId = (String) pMap.get("userId");
        String title = (String) pMap.get("title");
        String appoCode = (String) pMap.get("appoCode");

        //가져올 컬렉션 선택
        MongoCollection<Document> rCol = mongodb.getCollection("User");

        //쿼리 만들기
        Document findQuery = new Document();
        findQuery.append("userId", userId);

        Document updateQuery = new Document();
        String savecode = title + "*_*" + appoCode;

        updateQuery.append("appoList", savecode);

        UpdateResult updateResults = rCol.updateOne(findQuery, new Document("$pull", updateQuery));

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        int res = 1;
        AppoInfoDTO pDTO = null;
        if(redisDB.hasKey(appoCode)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(appoCode, 0, -1);
            pDTO = rList.get(0);
            Iterator<VoteInfoDTO> pList = pDTO.getUserlist().iterator();
            List<VoteInfoDTO> nList = new ArrayList<>();
            while(pList.hasNext()) {
                VoteInfoDTO vDTO = pList.next();
                String delid = vDTO.getUserid(); //나갈 아이디
                if(delid.equals(userId)) { //발견하면
                    log.info("삭제 : " + delid);
                } else {
                    log.info("유지 : " + delid);
                    nList.add(vDTO);
                }
            }

            if(nList.size() == 0) { //방에 사람이 없으면
                redisDB.delete(appoCode);
            } else {
                pDTO.setUserlist(nList);

                redisDB.opsForList().set(appoCode, 0, pDTO);
            }

        }else {
            res = 0;
        }

        return res;
    }

    @Override
    public AppoInfoDTO getAppoInfo(HashMap<String, Object> pMap) throws Exception {

        String appoCode = (String) pMap.get("appoCode");
        String userId = (String) pMap.get("userId");
        String title = (String) pMap.get("title");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        AppoInfoDTO rDTO = null;
        if(redisDB.hasKey(appoCode)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(appoCode, 0, -1);
            rDTO = rList.get(0);

            return rDTO;
        } else {

            //가져올 컬렉션 선택
            MongoCollection<Document> rCol = mongodb.getCollection("User");

            //쿼리 만들기
            Document findQuery = new Document();
            findQuery.append("userId", userId);

            Document updateQuery = new Document();
            String savecode = title + "*_*" + appoCode;

            updateQuery.append("appoList", savecode);

            UpdateResult updateResults = rCol.updateOne(findQuery, new Document("$pull", updateQuery));

            return null;
        }

    }

    @Override
    public HashMap<String, ArrayList<String>> getVoteInfo(Map<String, Object> pMap) throws Exception {
        log.info("mapper.getVoteInfo start");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        String userId = (String) pMap.get("userId");
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

                if(chgid.equals(userId)) { //발견하면
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

        String appoCode = (String) pMap.get("appoCode");
        String firday = (String) pMap.get("firday");
        String secday = (String) pMap.get("secday");
        String thiday = (String) pMap.get("thiday");

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        AppoInfoDTO pDTO = null;
        if(redisDB.hasKey(appoCode)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(appoCode, 0, -1);
            pDTO = rList.get(0); //정보를 pDTO에 저장
            pDTO.setFirdate(firday);
            pDTO.setSecdate(secday);
            pDTO.setThidate(thiday);
            redisDB.opsForList().set(appoCode, 0, pDTO); //투표정보 수정
        }
    }

    @Override
    public int inviteAppo(HashMap<String, Object> pMap) throws Exception { //0 : 방존재안함, 1 : 성공, 2 : 이미 해당방에 참가중

        String userId = (String) pMap.get("userId");
        String appoCode = (String) pMap.get("appoCode");
        String userName = (String) pMap.get("userName");

        int res = 0;

        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        AppoInfoDTO pDTO = null;
        boolean userExist = true;

        if(redisDB.hasKey(appoCode)) {
            List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(appoCode, 0, -1);
            pDTO = rList.get(0); //정보를 pDTO에 저장
            String title = pDTO.getTitle();
            Iterator<VoteInfoDTO> pList = pDTO.getUserlist().iterator();
            while(pList.hasNext()) {
                VoteInfoDTO vDTO = pList.next(); //투표정보리스트들 중 하나
                String chgid = vDTO.getUserid(); //바꿀 아이디

                if(chgid.equals(userId)) { //이미 해당 방에 유저가 있으면
                    userExist = false;
                    break;
                }
            }
            pList = null;
            if(userExist) { //방에 유저가 없다면 (== 유저를 추가시키기)
                //새로운 유저정보 DTO에 담기
                VoteInfoDTO vDTO = new VoteInfoDTO();
                vDTO.setUserid(userId);
                vDTO.setUsername(userName);
                vDTO.setVotetf(false);
                vDTO.setPosday(null);
                vDTO.setNegday(null);
                List<VoteInfoDTO> xList = pDTO.getUserlist();
                xList.add(vDTO);
                pDTO.setUserlist(xList);

                redisDB.opsForList().set(appoCode, 0, pDTO); //투표정보 수정

                /***
                 * 몽고(유저db)에 반영하기
                 */
                //가져올 컬렉션 선택
                MongoCollection<Document> rCol = mongodb.getCollection("User");


                //userDB에 약속방 추가
                Document query = new Document();
                query.append("userId", userId);

                Document updateQuery = new Document();
                String roomcode = title + "*_*" + appoCode;

                updateQuery.append("appoList", roomcode);

                UpdateResult updateResults = rCol.updateOne(query, new Document("$push", updateQuery));

                res = 1;

            } else {
                res = 2;
            }

        } else {
            res = 0;
        }


        return res;
    }
}
