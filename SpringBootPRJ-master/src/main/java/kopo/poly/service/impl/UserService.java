package kopo.poly.service.impl;

import kopo.poly.service.IUserService;
import kopo.poly.util.EncryptUtil;
import org.springframework.stereotype.Service;

@Service("UserService")
public class UserService implements IUserService {

    public int authLogin(String id, String pw) throws Exception{
        int res = 0;

        String hashedPw = EncryptUtil.encHashSHA256(pw);



        return res;
    }
}
