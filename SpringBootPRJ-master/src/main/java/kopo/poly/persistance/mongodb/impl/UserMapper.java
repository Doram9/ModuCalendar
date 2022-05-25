package kopo.poly.persistance.mongodb.impl;

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

@Slf4j
@Component("UserMapper")
public class UserMapper extends AbstractMongoDBComon implements IUserMapper {

    @Override
    public UserInfoDTO existUser(String userid, String userpw) throws Exception {

        int res = 0;

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userid", userid);
        query.append("userpw", userpw);

        Document projection = new Document();
        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        UserInfoDTO rDTO = new UserInfoDTO();
        for(Document doc : rs) {
            if(doc == null) {
                rDTO = null;
            }
            else {
                rDTO.setUserId(CmmUtil.nvl(doc.getString("userId")));
                rDTO.setUserName(CmmUtil.nvl(doc.getString("userName")));
                rDTO.setRegDt(CmmUtil.nvl(doc.getString("regDt")));
                rDTO.setUserEmail(CmmUtil.nvl(doc.getString("userEmail")));
                rDTO.setAppList(doc.getList("appList", String.class));
                rDTO.setPrjList(doc.getList("prjList", String.class));


            }
        }


        return rDTO;
    }

    //회원정보 조회
    @Override
    public UserInfoDTO getUserInfo(String userId) throws Exception{

        String colNm = "User";

        super.createCollection(colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userid", userId);

        Document projection = new Document();
        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        UserInfoDTO rDTO = new UserInfoDTO();

        for(Document doc : rs) {
            if(doc == null) {
                doc = new Document();
            }

            /***
             * //유저 아이디
             *     private String userId;
             *     //유저 비밀번호
             *     private String userPw;
             *     //유저 이메일
             *     private String userEmail;
             *     //유저 이름
             *     private String userName;
             *     //계정생성일
             *     private String regDt;
             *     //계정업데이트일
             *     private String updateDt;
             *     //약속방 목록
             *     private List<String> appList;
             *     //팀프로젝트방 목록
             *     private List<String> prjList;
             */
            rDTO.setUserId(CmmUtil.nvl(doc.getString("userId")));
            rDTO.setUserName(CmmUtil.nvl(doc.getString("userName")));
            rDTO.setRegDt(CmmUtil.nvl(doc.getString("regDt")));
            rDTO.setUserEmail(CmmUtil.nvl(doc.getString("userEmail")));



        }

        return rDTO;

    }


    public int regUser(UserInfoDTO pDTO) throws Exception{

        String colNm = "User";

        super.createCollection(colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);
        int res = 0;

        return res;
    }

}
