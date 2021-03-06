package kopo.poly.persistance.mongodb;

import kopo.poly.dto.UserInfoDTO;

public interface IUserMapper {

    //로그인 체크
    UserInfoDTO existUser(String userid, String userpw) throws Exception;

    //회원가입_id중복조회(1 : 중복없음, 0 : 중복있음)
    int existUser(String userid) throws Exception;

    //회원가입_email중복조회(1 : 중복없음, 0 : 중복있음)
    int existEmail(String userEmail) throws Exception;

    //회원정보 조회
    UserInfoDTO getUserInfo(String userId) throws Exception;
    //비밀번호 변경
    int resetPw(UserInfoDTO pDTO) throws Exception;

    //회원가입
    int regUser(UserInfoDTO pDTO) throws Exception;

    //회원정보 전체삭제
    int deleteUser(UserInfoDTO pDTO) throws Exception;
    //약속방 추가

    //약속방 삭제

    //팀프로젝트 추가

    //팀프로젝트 삭제


}
