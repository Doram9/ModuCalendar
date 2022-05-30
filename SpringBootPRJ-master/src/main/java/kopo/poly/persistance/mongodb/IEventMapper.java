package kopo.poly.persistance.mongodb;

import kopo.poly.dto.EventDTO;

public interface IEventMapper {

    int addEvent(String userId, EventDTO pDTO) throws Exception;

    int deleteEvent(String userId, String evnetId) throws Exception;
}
