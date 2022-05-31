package kopo.poly.persistance.redis;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface IAppoMapper {

    int createAppo(AppoInfoDTO rDTO, UserInfoDTO uDTO) throws Exception;

    void deleteAppo(HashMap<String, Object> pMap) throws Exception;

    AppoInfoDTO getAppoInfo(String code) throws Exception;

    HashMap<String, ArrayList<String>> getVoteInfo(Map<String, Object> pMap) throws Exception;

    void updateResult(Map<String, Object> pMap) throws Exception;
}
