package kopo.poly.service.impl;

import kopo.poly.dto.EventDTO;
import kopo.poly.persistance.mongodb.IEventMapper;
import kopo.poly.persistance.redis.IAppoMapper;
import kopo.poly.service.IEventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service("EventService")
public class EventService implements IEventService {

    @Resource(name="EventMapper")
    IEventMapper eventMapper;

    //일정 추가
    @Override
    public int addEvent(String userId, EventDTO pDTO) throws Exception {

        log.info("service.addEvent start");
        int res = eventMapper.addEvent(userId, pDTO);

        return res;
    }

    @Override
    public int deleteEvent(String userId, String eventId) throws Exception {
        log.info("service.deleteEvent start");

        int res = eventMapper.deleteEvent(userId, eventId);

        return res;
    }
}
