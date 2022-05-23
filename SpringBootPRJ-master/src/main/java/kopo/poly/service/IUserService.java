package kopo.poly.service;

import kopo.poly.dto.UserInfoDTO;

public interface IUserService {

    //로그인 정보 확인(1 : 조회성공, 0 : 조회실패
    int authLogin(String id, String pw) throws Exception;

    //회원정보 조회
    UserInfoDTO getUserInfo(String userId) throws Exception;

    //비밀번호 변경

    //회원정보 전체삭제

    //약속방 추가

    //약속방 삭제

    //팀프로젝트 추가

    //팀프로젝트 삭제
}
