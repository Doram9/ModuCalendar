package kopo.poly.persistance.mongodb;

import kopo.poly.dto.ChatMessageDTO;
import kopo.poly.dto.PlayerInfoDTO;
import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.dto.UserInfoDTO;

import java.util.List;

public interface IPrjMapper {

    //프로젝트생성
    int createPrj(PrjInfoDTO pDTO, String userId) throws Exception;
    //프로젝트 삭제
    int deletePrj(PrjInfoDTO pDTO, String userId) throws Exception;

    //프로젝트 정보 가져오기
    PrjInfoDTO getPrjInfo(PrjInfoDTO pDTO) throws Exception;

    //팀원 추가
    int invitePlayer(PrjInfoDTO pDTO, PlayerInfoDTO iDTO) throws Exception;


    //팀원 추방
    int deletePlayer(PrjInfoDTO pDTO, UserInfoDTO uDTO) throws Exception;


    //마일스톤 수정
    int updatePrj(PrjInfoDTO pDTO) throws Exception;

    //마일스톤 삭제
    int deleteMile(PrjInfoDTO pDTO) throws Exception;

    //팀원정보 수정
    int updatePlayerInfo(PrjInfoDTO jDTO, PlayerInfoDTO pDTO) throws Exception;

    //프로젝트 채팅로그
    List<ChatMessageDTO> getMessageList(String prjCode) throws Exception;


    //프로젝트 나가기
    int getoutPlayer(PrjInfoDTO pDTO, UserInfoDTO uDTO) throws Exception;
}
