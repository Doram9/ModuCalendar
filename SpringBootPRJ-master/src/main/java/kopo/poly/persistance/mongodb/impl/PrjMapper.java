package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.MileDTO;
import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IPrjMapper;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.Map;


@Slf4j
@Component("PrjMapper")
public class PrjMapper extends AbstractMongoDBComon implements IPrjMapper {

    @Override
    public int createPrj(PrjInfoDTO pDTO, String userId) throws Exception {
        MileDTO mDTO = new MileDTO();
        pDTO.setPrjMileInfo(mDTO);

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
        String prjCode = pDTO.getPrjCode();

        updateQuery.append("prjList", prjCode);

        UpdateResult updateResults = rCol.updateOne(query, new Document("$push", updateQuery));
        int res = (int) updateResults.getMatchedCount();

        return res;
    }

    @Override
    public int deletePrj(PrjInfoDTO pDTO) throws Exception {
        return 0;
    }
}
