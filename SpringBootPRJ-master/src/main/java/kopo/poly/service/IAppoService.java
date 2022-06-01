package kopo.poly.service;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.HashMap;

public interface IAppoService {

    int createAppo(AppoInfoDTO pDTO, UserInfoDTO uDTO) throws Exception;

    int deleteAppo(HashMap<String, Object> pMap) throws Exception;

    AppoInfoDTO getAppoInfo(HashMap<String, Object> pMap) throws Exception;

    int voteAppo(HashMap<String, Object> pMap) throws Exception;

    int inviteAppo(HashMap<String, Object> pMap) throws Exception;

}
