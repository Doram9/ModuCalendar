package kopo.poly.persistance.mongodb;

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

    //프로젝트 게시판 글 생성
    //프로젝트 게시판 글 수정
    //프로젝트 게시판 글 삭제

    //프로젝트 채팅로그
}
