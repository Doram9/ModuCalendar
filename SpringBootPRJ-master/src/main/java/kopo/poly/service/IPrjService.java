package kopo.poly.service;

import kopo.poly.dto.PrjInfoDTO;

public interface IPrjService {

    int createPrj(PrjInfoDTO pDTO, String userId) throws Exception;

    int updateMile(PrjInfoDTO pDTO) throws Exception;

    PrjInfoDTO getPrjInfo(PrjInfoDTO pDTO) throws Exception;
}
