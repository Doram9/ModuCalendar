package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.*;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IPrjMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@Component("PrjMapper")
public class PrjMapper extends AbstractMongoDBComon implements IPrjMapper {

    @Override
    public int createPrj(PrjInfoDTO pDTO, String userId) throws Exception {
        log.info("mapper.createPrj start");
        String prjCode = pDTO.getPrjCode();
        String prjTitle = pDTO.getPrjTitle();

        String colNm = "Prj";

        super.createCollection(colNm);

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        col.insertOne(new Document(new ObjectMapper().convertValue(pDTO, Map.class)));


        //가져올 컬렉션 선택
        MongoCollection<Document> rCol = mongodb.getCollection("User");


        //userDB에 약속방 추가
        Document query = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();
        updateQuery.append("prjList", prjTitle + "*_*" + prjCode);

        UpdateResult updateResults = rCol.updateOne(query, new Document("$push", updateQuery));
        int res = (int) updateResults.getMatchedCount();

        return res;
    }

    @Override
    public int deletePrj(PrjInfoDTO pDTO, String userId) throws Exception {
        log.info("mapper.deletePrj start");
        String prjCode = pDTO.getPrjCode();
        String prjTitle = pDTO.getPrjTitle();

        String colNm = "Prj";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("prjCode", prjCode);

        DeleteResult rs = col.deleteOne(query);



        String UcolNm = "User";

        MongoCollection<Document> Ucol = mongodb.getCollection(UcolNm);

        Document Uquery = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();
        String savecode = prjTitle + "*_*" + prjCode;
        log.info(savecode);
        updateQuery.append("prjList", savecode);

        UpdateResult res = Ucol.updateOne(Uquery, new Document("$pull", updateQuery));

        int ress = (int) rs.getDeletedCount();

        return ress;
    }

    @Override
    public PrjInfoDTO getPrjInfo(PrjInfoDTO pDTO) throws Exception {
        log.info("mapper.getPrjInfo start");
        String colNm = "Prj";
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String prjCode = pDTO.getPrjCode();

        Document query = new Document();
        query.append("prjCode", prjCode);

        Document projection = new Document();
        projection.append("_id", 0);

        FindIterable<Document> rs = col.find(query).projection(projection);

        if(rs != null) {
            log.info("프로젝트 데이터 존재");
            PrjInfoDTO rDTO = new PrjInfoDTO();

            for(Document doc : rs) {
                if(doc == null) {
                    doc = new Document();
                }
                rDTO.setPrjCode(CmmUtil.nvl(doc.getString("prjCode")));
                rDTO.setPrjStartDate(CmmUtil.nvl(doc.getString("prjStartDate")));
                rDTO.setPrjEndDate(CmmUtil.nvl(doc.getString("prjEndDate")));
                rDTO.setPrjTitle(CmmUtil.nvl(doc.getString("prjTitle")));
                rDTO.setPrjRegDt(CmmUtil.nvl(doc.getString("prjRegDt")));

                List<Document> pList = doc.getList("prjPlayer", Document.class);
                List<Document> mList = doc.getList("prjMileInfo", Document.class);

                List<PlayerInfoDTO> rList = new ArrayList<>();
                for(Document pdoc : pList) {

                    PlayerInfoDTO iDTO = new PlayerInfoDTO();
                    iDTO.setUserId(pdoc.getString("userId"));
                    iDTO.setUserName(pdoc.getString("userName"));
                    iDTO.setUserRole(pdoc.getString("userRole"));
                    iDTO.setUserGrant(pdoc.getString("userGrant"));

                    rList.add(iDTO);

                    iDTO = null;
                }
                rDTO.setPrjPlayer(rList);

                List<MileDTO> tList = new ArrayList<>();
                for(Document mdoc : mList) {
                    MileDTO mDTO = new MileDTO();
                    mDTO.setItemValue(mdoc.getString("itemValue"));
                    mDTO.setItemStartDate(mdoc.getString("itemStartDate"));
                    mDTO.setItemEndDate(mdoc.getString("itemEndDate"));

                    tList.add(mDTO);
                    mDTO = null;
                }
                rDTO.setPrjMileInfo(tList);
                pList = null;
                tList = null;
            }
            log.info("mapper.getPrjInfo end");
            return rDTO;
        } else {
            log.info("프로젝트 데이터 없음");
            return null;
        }

    }

    @Override
    public int invitePlayer(PrjInfoDTO pDTO, PlayerInfoDTO iDTO) throws Exception {
        log.info("mapper.invitePlayer start");
        String colNm = "Prj";
        MongoCollection<Document> col = mongodb.getCollection(colNm);
        String uColNm = "User";
        MongoCollection<Document> ucol = mongodb.getCollection(uColNm);

        String prjCode = pDTO.getPrjCode();
        log.info("prjCode : " + prjCode);
        String userId = iDTO.getUserId();
        log.info("userId : " + userId);

        Document Pquery = new Document();
        Pquery.append("prjCode", prjCode);

        FindIterable<Document> rs = col.find(Pquery);

        //참가하려는 프로젝트가 존재하면
        if(rs != null) {
            log.info("프로젝트 존재");
            PrjInfoDTO rDTO = new PrjInfoDTO();
            for(Document doc : rs) {
                if (doc == null) {
                    doc = new Document();
                }
                rDTO.setPrjTitle(CmmUtil.nvl(doc.getString("prjTitle")));
                List<Document> pList = doc.getList("prjPlayer", Document.class);

                for(Document pdoc : pList) {
                    if(userId.equals(pdoc.getString("userId"))) {
                        log.info("이미 프로젝트에 참가중");
                        return 2;
                    }
                }
            }

            Document inputQuery = new Document();
            inputQuery.append("prjCode", prjCode);

            ObjectMapper mapper = new ObjectMapper();
            Document dto = mapper.convertValue(iDTO, Document.class);
            Document inputValueQuery = new Document();
            inputValueQuery.append("prjPlayer", dto);

            UpdateResult rrss = col.updateOne(inputQuery, new Document("$push", inputValueQuery));
            log.info("프로젝트 팀원 추가 결과 : " + rrss);

            String prjTitle = rDTO.getPrjTitle();

            Document Uquery = new Document();
            Uquery.append("userId", userId);

            Document updateQuery = new Document();
            updateQuery.append("prjList", prjTitle + "*_*" + prjCode);

            UpdateResult res = ucol.updateOne(Uquery, new Document("$push", updateQuery));
            int urs = (int) res.getMatchedCount();
            log.info("urs : " + urs);
            return urs;
        } else {
            return 0;
        }
    }

    @Override
    public int deletePlayer(PrjInfoDTO pDTO, UserInfoDTO uDTO) throws Exception {
        log.info("mapper.deletePlayer start");
        String colNm = "Prj";
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String prjCode = pDTO.getPrjCode();
        String userId = uDTO.getUserId();

        Document query = new Document();
        query.append("prjCode", prjCode);

        Document uQuery = new Document();
        uQuery.append("prjPlayer", new Document("userId", userId));

        UpdateResult rs = col.updateMany(query, new Document("$pull", uQuery));

        int res = (int) rs.getMatchedCount();
        log.info("res : " + res);
        return res;
    }

    @Override
    public int updatePrj(PrjInfoDTO pDTO) throws Exception {
        log.info("mapper.updatePrj start");
        String colNm = "Prj";
        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String prjCode = pDTO.getPrjCode();
        String prjStartDate = pDTO.getPrjStartDate();
        String prjEndDate = pDTO.getPrjEndDate();
        List<MileDTO> mList = pDTO.getPrjMileInfo();
        List<Document> dList = new ArrayList<>();
        for(MileDTO mDTO : mList) {
            Document dtoQuery = new Document();
            dtoQuery.append("itemValue", mDTO.getItemValue());
            dtoQuery.append("itemStartDate", mDTO.getItemStartDate());
            dtoQuery.append("itemEndDate", mDTO.getItemEndDate());

            dList.add(dtoQuery);
        }

        Document query = new Document();
        query.append("prjCode", prjCode);

        Document updateQuery = new Document();
        updateQuery.append("prjStartDate", prjStartDate);
        updateQuery.append("prjEndDate", prjEndDate);
        updateQuery.append("prjMileInfo", dList);

        mList = null;
        dList = null;

        UpdateResult updateResults = col.updateMany(query, new Document("$set", updateQuery));

        int res = (int) updateResults.getMatchedCount();
        log.info("mapper.updatePrj end");
        return res;
    }

    @Override
    public int deleteMile(PrjInfoDTO pDTO) throws Exception {
        log.info("mapper.deleteMile start");
        String colNm = "Prj";
        MongoCollection<Document> col = mongodb.getCollection(colNm);
        String prjCode = pDTO.getPrjCode();

        Document query = new Document();
        query.append("prjCode", prjCode);

        List<MileDTO> mList = pDTO.getPrjMileInfo();
        List<Document> dList = new ArrayList<>();
        for(MileDTO mDTO : mList) {
            Document dtoQuery = new Document();
            dtoQuery.append("itemValue", mDTO.getItemValue());
            dtoQuery.append("itemStartDate", mDTO.getItemStartDate());
            dtoQuery.append("itemEndDate", mDTO.getItemEndDate());

            dList.add(dtoQuery);
        }

        Document updateQuery = new Document();
        updateQuery.append("prjMileInfo", dList);

        UpdateResult updateResults = col.updateOne(query, new Document("$set", updateQuery));

        int res = (int) updateResults.getMatchedCount();
        log.info("mapper.deleteMile end");
        return res;
    }

    @Override
    public int updatePlayerInfo(PrjInfoDTO jDTO, PlayerInfoDTO pDTO) throws Exception {
        log.info("mapper.updatePlayerInfo start");
        String colNm = "Prj";
        MongoCollection<Document> col = mongodb.getCollection(colNm);
        String prjCode = jDTO.getPrjCode();
        String userId = pDTO.getUserId();

        Document query = new Document();
        query.append("prjCode", prjCode);
        query.append("prjPlayer.userId", userId);

        Document updateQuery = new Document();

        ObjectMapper mapper = new ObjectMapper();
        Document dto = mapper.convertValue(pDTO, Document.class);

        updateQuery.append("prjPlayer.$", dto);


        UpdateResult updateResults = col.updateOne(query, new Document("$set", updateQuery));

        int res = (int) updateResults.getMatchedCount();
        log.info("res : " + res);
        log.info("mapper.updatePlayerInfo end");
        return res;
    }

    @Override
    public int getoutPlayer(PrjInfoDTO pDTO, UserInfoDTO uDTO) throws Exception {
        log.info("mapper.updatePlayerInfo start");
        String userId = uDTO.getUserId();
        String prjCode = pDTO.getPrjCode();
        String prjTitle = pDTO.getPrjTitle();

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        String savecode = prjTitle + "*_*" + prjCode;
        Document updateQuery = new Document();
        updateQuery.append("prjList", savecode);

        UpdateResult rs = col.updateOne(query, new Document("$pull", updateQuery));

        int res = (int) rs.getMatchedCount();

        return res;
    }

}
