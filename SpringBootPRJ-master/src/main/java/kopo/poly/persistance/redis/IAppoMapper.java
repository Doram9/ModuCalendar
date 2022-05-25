package kopo.poly.persistance.redis;

import kopo.poly.dto.AppoInfoDTO;

import java.util.HashMap;

public interface IAppoMapper {

    public int createAppo(AppoInfoDTO rDTO, String userid) throws Exception;

    public void deleteAppo(HashMap<String, Object> pMap) throws Exception;

    public AppoInfoDTO getAppoInfo(String roomcode) throws Exception;
}
