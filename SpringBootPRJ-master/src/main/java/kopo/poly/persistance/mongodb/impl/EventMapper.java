package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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

        ObjectMapper mapper = new ObjectMapper();
        Document dto = mapper.convertValue(pDTO, Document.class);

        updateQuery.append("eventList", dto);

        UpdateResult rs = col.updateOne(query, new Document("$push", updateQuery));

        int res = (int) rs.getMatchedCount();

        return res;
    }

    @Override
    public int deleteEvent(String userId, String evnetId) throws Exception {

        log.info("mapper.deleteEvent start");
        log.info("userId : " + userId);
        log.info("evnetId : " + evnetId);

        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        Document query = new Document();
        query.append("userId", userId);

        Document updateQuery = new Document();
        updateQuery.append("eventList", new Document().append("event_id", evnetId));

        UpdateResult rs = col.updateOne(query, new Document("$pull", updateQuery));

        int res = (int) rs.getMatchedCount();

        return res;
    }
}
