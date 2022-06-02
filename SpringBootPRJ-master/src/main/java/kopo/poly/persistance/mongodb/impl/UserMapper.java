package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.EventDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.dto.VoteInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IUserMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("UserMapper")
public class UserMapper extends AbstractMongoDBComon implements IUserMapper {

    public final RedisTemplate<String, Object> redisDB;

    public UserMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }

    //로그인
    @Override
    public UserInfoDTO existUser(String userid, String userpw) throws Exception {

        log.info("userMapper.existUser start");

        log.info("userid : " + userid);
        log.info("userpw : " + userpw);

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userid);
        query.append("userPw", userpw);

        Document projection = new Document();
        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        UserInfoDTO rDTO = new UserInfoDTO();
        //해당하는 데이터 없으면 for문 안돔
        for(Document doc : rs) {
            if(doc == null) {
                doc = new Document();
            }

            rDTO.setUserId(CmmUtil.nvl(doc.getString("userId")));
            rDTO.setUserName(CmmUtil.nvl(doc.getString("userName")));
            rDTO.setRegDt(CmmUtil.nvl(doc.getString("regDt")));
            rDTO.setUserEmail(CmmUtil.nvl(doc.getString("userEmail")));


        }


        return rDTO;
    }

    //회원가입_id중복조회(1 : 중복없음, 0 : 중복있음)
    @Override
    public int existUser(String userid) throws Exception {

        log.info("mapper.existUser start");

        String colNm = "User";

        super.createCollection(colNm);

        int res = 1;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userid);

        FindIterable<Document> rs = col.find(query);

        for(Document doc : rs) {
            res = 0;
        }

        return res;
    }

    //회원가입_email중복조회(1 : 중복없음, 0 : 중복있음)
    public int existEmail(String userEmail) throws Exception{

        log.info("mapper.existEmail start");

        String colNm = "User";

        super.createCollection(colNm);

        int res = 1;

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userEmail", userEmail);

        FindIterable<Document> rs = col.find(query);

        for(Document doc : rs) {
            res = 0;
        }

        log.info("mapper_res : " + res);
        return res;

    }

    //회원정보 조회
    @Override
    public UserInfoDTO getUserInfo(String userId) throws Exception{

        String colNm = "User";

        super.createCollection(colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        Document projection = new Document();
        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        UserInfoDTO rDTO = new UserInfoDTO();

        for(Document doc : rs) {
            if(doc == null) {
                doc = new Document();
            }

            rDTO.setUserId(CmmUtil.nvl(doc.getString("userId")));
            rDTO.setUserName(CmmUtil.nvl(doc.getString("userName")));
            rDTO.setRegDt(CmmUtil.nvl(doc.getString("regDt")));
            rDTO.setUserEmail(CmmUtil.nvl(doc.getString("userEmail")));
            rDTO.setAppoList(doc.getList("appoList", String.class));
            rDTO.setPrjList(doc.getList("prjList", String.class));

            List<Document> eList = doc.getList("eventList", Document.class);

            ArrayList<EventDTO> rList = new ArrayList<>();
            for(Document edoc : eList) {

                EventDTO eDTO = new EventDTO();
                eDTO.setEvent_id(edoc.getString("event_id"));
                eDTO.setTitle(edoc.getString("title"));
                eDTO.setStart(edoc.getString("start"));
                eDTO.setEnd(edoc.getString("end"));

                rList.add(eDTO);

                eDTO = null;
            }

            rDTO.setEventList(rList);

        }

        return rDTO;

    }

    @Override
    public int resetPw(UserInfoDTO pDTO) throws Exception {

        log.info("mapper.resetPw start");

        String userId = pDTO.getUserId();
        String userPw = pDTO.getUserPw();

        log.info("userId : " + userId);
        log.info("userPw : " + userPw);

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();

        updateQuery.append("userPw", userPw);

        UpdateResult rs = col.updateOne(query, new Document("$set", updateQuery));

        log.info("rs : " + rs);
        log.info("mapper.resetPw end");

        int res = (int) rs.getMatchedCount();

        return res;

    }

    //회원가입
    @Override
    public int regUser(UserInfoDTO pDTO) throws Exception{

        String colNm = "User";

        super.createCollection(colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);


        int res = 0;

        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));

        res = 1;

        return res;
    }

    @Override
    public int deleteUser(UserInfoDTO pDTO) throws Exception {

        log.info("mapper.deleteUser start");

        String userId = pDTO.getUserId();
        List<String> appoList = pDTO.getAppoList();
        if(appoList == null) {
            log.info("appoList = null");
            appoList = new ArrayList<>();
        }
        List<String> prjList = pDTO.getPrjList();
        if(prjList == null) {
            prjList = new ArrayList<>();
        }


        //사용자가 속해있는 약속 방에 사용자 정보 제거
        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(AppoInfoDTO.class));

        for(String appoTitleCode : appoList) {

            String[] appoArray = appoTitleCode.split("\\*_\\*");
            String appoCode = appoArray[1];

            AppoInfoDTO aDTO = null;
            if(redisDB.hasKey(appoCode)) {
                List<AppoInfoDTO> rList = (List) redisDB.opsForList().range(appoCode, 0, -1);
                aDTO = rList.get(0);
                Iterator<VoteInfoDTO> pList = aDTO.getUserlist().iterator();
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
                    aDTO.setUserlist(nList);

                    redisDB.opsForList().set(appoCode, 0, aDTO);
                }

            }
        }

        //사용자가 속해있는 프로젝트 방에 사용자 정보 제거


        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        DeleteResult rs = col.deleteOne(query);

        log.info("rs : " + rs);
        log.info("mapper.resetPw end");

        int res = (int) rs.getDeletedCount();

        return res;
    }


}
