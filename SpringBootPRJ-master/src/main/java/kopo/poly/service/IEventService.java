package kopo.poly.service;

import kopo.poly.dto.EventDTO;
import kopo.poly.dto.MileDTO;
import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.List;

public interface IEventService {

    int addEvent(String userId, EventDTO pDTO) throws Exception;

    int deleteEvent(String userId, String eventId) throws Exception;

    int insertMile(String userId, PrjInfoDTO pDTO) throws Exception;
}
