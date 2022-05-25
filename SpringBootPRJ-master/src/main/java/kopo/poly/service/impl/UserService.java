package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.IUserMapper;
import kopo.poly.service.IUserService;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
@Slf4j
public class UserService implements IUserService {

    @Resource(name="UserMapper")
    private IUserMapper userMapper;

    @Override
    public UserInfoDTO authLogin(String id, String pw) throws Exception{

        String hashedPw = EncryptUtil.encHashSHA256(pw);

        UserInfoDTO rDTO = userMapper.existUser(id, hashedPw);


        return rDTO;
    }


    //회원정보 조회
    @Override
    public UserInfoDTO getUserInfo(String userId) throws Exception{

        UserInfoDTO rDTO = new UserInfoDTO();

        rDTO = userMapper.getUserInfo(userId);



        return rDTO;
    }

    //회원가입_id중복조회(1 : 중복없음, 0 : 중복있음)
    @Override
    public int checkingId(String checkId) throws Exception {

        log.info("service.checkingId start");
        int res = userMapper.existUser(checkId);

        return res;
    }

    //회원가입
    @Override
    public int regUser(UserInfoDTO pDTO) throws Exception{

        int res = userMapper.regUser(pDTO);

        return res;
    }
}
