package kopo.poly.service;

import kopo.poly.dto.EventDTO;

public interface IEventService {

    int addEvent(String userId, EventDTO pDTO) throws Exception;

    int deleteEvent(String userId, String eventId) throws Exception;
}
