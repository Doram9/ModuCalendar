package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IUserMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("UserMapper")
public class UserMapper extends AbstractMongoDBComon implements IUserMapper {

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
            rDTO.setAppList(doc.getList("appList", String.class));
            rDTO.setPrjList(doc.getList("prjList", String.class));

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



        }

        return rDTO;

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


}
