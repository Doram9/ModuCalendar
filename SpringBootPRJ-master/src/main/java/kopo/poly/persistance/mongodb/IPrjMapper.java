package kopo.poly.persistance.mongodb;

import kopo.poly.dto.PlayerInfoDTO;
import kopo.poly.dto.PrjInfoDTO;

public interface IPrjMapper {

    //프로젝트생성
    int createPrj(PrjInfoDTO pDTO, String userId) throws Exception;
    //프로젝트 삭제
    int deletePrj(PrjInfoDTO pDTO) throws Exception;

    //프로젝트 정보 가져오기
    PrjInfoDTO getPrjInfo(PrjInfoDTO pDTO) throws Exception;

    //마일스톤 생성

    //마일스톤 수정
    int updatePrj(PrjInfoDTO pDTO) throws Exception;

    //마일스톤 삭제

    //팀원정보 수정
    int updatePlayerInfo(PrjInfoDTO jDTO, PlayerInfoDTO pDTO) throws Exception;

    //프로젝트 채팅로그
}
