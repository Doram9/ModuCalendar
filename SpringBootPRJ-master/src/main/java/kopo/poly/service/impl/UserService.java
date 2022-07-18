package kopo.poly.service.impl;

import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.persistance.mongodb.IPrjMapper;
import kopo.poly.persistance.mongodb.IUserMapper;
import kopo.poly.service.IUserService;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("UserService")
@Slf4j
public class UserService implements IUserService {

    @Resource(name="UserMapper")
    private IUserMapper userMapper;

    @Resource(name="PrjMapper")
    private IPrjMapper prjMapper;

    //로그인
    @Override
    public UserInfoDTO authLogin(String id, String pw) throws Exception{

        String hashedPw = EncryptUtil.encHashSHA256(pw);

        UserInfoDTO rDTO = userMapper.existUser(id, hashedPw);


        return rDTO;
    }


    //회원정보 조회
    @Override
    public UserInfoDTO getUserInfo(String userId) throws Exception{

        UserInfoDTO rDTO = userMapper.getUserInfo(userId);

        return rDTO;
    }

    //회원가입_id중복조회(1 : 중복없음, 0 : 중복있음)
    @Override
    public int checkingId(String checkId) throws Exception {

        log.info("service.checkingId start");
        int res = userMapper.existUser(checkId);

        return res;
    }

    //회원가입_이메일 중복조회(1 : 중복없음, 0 : 중복있음)
    public int checkingEmail(String checkEmail) throws Exception{

        log.info("service.checkingEmail start");
        int res = userMapper.existEmail(checkEmail);

        return res;
    }

    //회원가입
    @Override
    public int regUser(UserInfoDTO pDTO) throws Exception{

        int res = userMapper.regUser(pDTO);

        return res;
    }

    @Override
    public int resetPw(UserInfoDTO pDTO) throws Exception {
        log.info("service.resetPw start");

        int res = userMapper.resetPw(pDTO);

        return res;
    }

    @Override
    public int deleteUser(UserInfoDTO uDTO) throws Exception {
        log.info("service.deleteUser start");

        List<String> prjList = uDTO.getPrjList();

        for(String prjTitleCode : prjList) {
            String prjCode = prjTitleCode.split("\\*_\\*")[1];
            PrjInfoDTO pDTO = new PrjInfoDTO();
            pDTO.setPrjCode(prjCode);
            prjMapper.deletePlayer(pDTO, uDTO);
        }

        int res = userMapper.deleteUser(uDTO);
        return res;
    }

    @Override
    @Async
    public void sendMail(String userEmail) throws Exception {
        String subTitle = "모두캘린더 비밀번호 변경";
        String body = "http://3.37.211.223:11000/resetPw?resetCode=" + EncryptUtil.encAES128CBC(userEmail);
        log.info("send mail body : " + body);
        int res = MailUtil.sendAuthEmail(userEmail, subTitle, body);
        log.info("res : " + res);
    }
}
