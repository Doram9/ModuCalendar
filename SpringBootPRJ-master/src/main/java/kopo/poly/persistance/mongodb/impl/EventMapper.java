package kopo.poly.persistance.mongodb.impl;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.EventDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IEventMapper;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Slf4j
@Component("EventMapper")
public class EventMapper extends AbstractMongoDBComon implements IEventMapper {


    @Override
    public int addEvent(String userId, EventDTO pDTO) throws Exception {

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();
        updateQuery.append("eventList", pDTO);


        UpdateResult rs = col.updateOne(query, new Document("$push", updateQuery));

        int res = (int) rs.getMatchedCount();

        return res;
    }

    @Override
    public int deleteEvent(String userId, String evnetId) throws Exception {

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();
        updateQuery.append("eventList", new Document().append("id", evnetId));

        UpdateResult rs = col.updateOne(query, new Document("$pull", updateQuery));

        int res = (int) rs.getMatchedCount();

        return res;
    }
}
