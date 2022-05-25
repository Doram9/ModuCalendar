package kopo.poly.service.impl;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.IUserMapper;
import kopo.poly.service.IUserService;
import kopo.poly.util.EncryptUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
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
}
