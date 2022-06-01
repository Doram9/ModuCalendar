package kopo.poly.persistance.redis;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public interface IAppoMapper {

    int createAppo(AppoInfoDTO rDTO, UserInfoDTO uDTO) throws Exception;

    int deleteAppo(HashMap<String, Object> pMap) throws Exception;

    AppoInfoDTO getAppoInfo(HashMap<String, Object> pMap) throws Exception;

    HashMap<String, ArrayList<String>> getVoteInfo(Map<String, Object> pMap) throws Exception;

    void updateResult(Map<String, Object> pMap) throws Exception;

    int inviteAppo(HashMap<String, Object> pMap) throws Exception;

}
