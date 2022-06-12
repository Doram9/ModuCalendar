package kopo.poly.persistance.mongodb;

import kopo.poly.dto.EventDTO;
import kopo.poly.dto.MileDTO;
import kopo.poly.dto.PrjInfoDTO;

import java.util.List;

public interface IEventMapper {

    int addEvent(String userId, EventDTO pDTO) throws Exception;

    int deleteEvent(String userId, String evnetId) throws Exception;

    int insertMile(String userId, PrjInfoDTO pDTO) throws Exception;

}