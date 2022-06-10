package kopo.poly.service;

import kopo.poly.dto.PlayerInfoDTO;
import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.dto.UserInfoDTO;

public interface IPrjService {

    int createPrj(PrjInfoDTO pDTO, String userId) throws Exception;

    int deletePrj(PrjInfoDTO pDTO, String userId) throws Exception;

    int updateMile(PrjInfoDTO pDTO) throws Exception;

    int deleteMile(PrjInfoDTO pDTO) throws Exception;

    PrjInfoDTO getPrjInfo(PrjInfoDTO pDTO) throws Exception;

    int updatePlayerInfo(PrjInfoDTO jDTO, PlayerInfoDTO pDTO) throws Exception;

    int deletePlayer(PrjInfoDTO pDTO, UserInfoDTO uDTO) throws Exception;

    int invitePlayer(PrjInfoDTO pDTO, PlayerInfoDTO iDTO) throws Exception;

    int getoutPlayer(PrjInfoDTO pDTO, UserInfoDTO uDTO) throws Exception;
}
