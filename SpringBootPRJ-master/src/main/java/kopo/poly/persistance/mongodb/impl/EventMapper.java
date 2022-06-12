package kopo.poly.persistance.mongodb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.UpdateResult;
import kopo.poly.dto.EventDTO;
import kopo.poly.dto.MileDTO;
import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.AbstractMongoDBComon;
import kopo.poly.persistance.mongodb.IEventMapper;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Component;

import java.util.List;

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

    @Override
    public int insertMile(String userId, PrjInfoDTO pDTO) throws Exception {
        log.info("mapper.insertMile start");

        String eventId = pDTO.getPrjCode();
        List<MileDTO> mList = pDTO.getPrjMileInfo();
        String colNm = "User";

        MongoCollection<Document> col = mongodb.getCollection(colNm);
        Document query = new Document();
        query.append("userId", userId);

        int res = 0;
        for(MileDTO mDTO : mList) {
            EventDTO eDTO = new EventDTO();
            eDTO.setEvent_id(eventId);
            eDTO.setStart(mDTO.getItemStartDate());
            String add1DayEndDate = DateUtil.addDateYMD(mDTO.getItemEndDate(), 1);
            eDTO.setEnd(add1DayEndDate);
            eDTO.setTitle(mDTO.getItemValue());

            ObjectMapper mapper = new ObjectMapper();
            Document dto = mapper.convertValue(eDTO, Document.class);

            Document updateQuery = new Document();
            updateQuery.append("eventList", dto);

            UpdateResult rs = col.updateOne(query, new Document("$push", updateQuery));

            res += (int) rs.getMatchedCount();
        }
        return res;
    }
}
