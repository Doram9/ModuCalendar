package kopo.poly.service;

public interface IUserService {

    //로그인 정보 확인(1 : 조회성공, 0 : 조회실패
    int authLogin(String id, String pw) throws Exception;
}
