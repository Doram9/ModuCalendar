package kopo.poly.service;

import kopo.poly.dto.AppoInfoDTO;

import java.util.HashMap;

public interface IAppoService {

    public int createAppo(AppoInfoDTO pDTO, String userid, String username) throws Exception;

    public void deleteAppo(HashMap<String, Object> pMap) throws Exception;

    public AppoInfoDTO getAppoInfo(String roomcode) throws Exception;
}
