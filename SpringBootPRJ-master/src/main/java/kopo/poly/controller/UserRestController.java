package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.jasper.tagplugins.jstl.core.Url;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class UserRestController {

    @Resource(name = "UserService")
    private IUserService userSevice;

    //로그인 정보
    @GetMapping(value = "dologin")
    public String login(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.dologin start");

        String id = request.getParameter("reqId");
        String pw = request.getParameter("reqPw");

        UserInfoDTO pDTO = userSevice.authLogin(id, pw);

        if(pDTO.getUserId() == null) {
            return "fail";
        } else {
            log.info("로그인성공");
            session.setAttribute("userId", pDTO.getUserId());
            String userName = pDTO.getUserName();
            log.info("userName : " + userName);
            session.setAttribute("userName", userName);

            return "success";
        }

    }

    //회원가입_ID중복체크
    @RequestMapping(value = "checkId")
    public String checkId(@RequestParam("checkId") String checkId) throws Exception {

        log.info("checkId : " + checkId);

        int res = userSevice.checkingId(checkId);

        return "" + res;
    }

    //회원가입_이메일중복체크
    @RequestMapping(value = "checkEmail")
    public String checkEmail(@RequestParam("checkEmail") String checkEmail) throws Exception {

        log.info("checkEmail : " + checkEmail);

        int res = userSevice.checkingEmail(checkEmail);

        return "" + res;
    }

    //비밀번호 재설정
    @GetMapping(value = "doResetPw")
    public String doResetPw(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller.doResetPw start");
        log.info("resetCode : " + CmmUtil.nvl(request.getParameter("resetCode")));
        String resetCode = EncryptUtil.decAES128CBC(request.getParameter("resetCode"));
        log.info("resetCode : " + resetCode);
        String resetPw = CmmUtil.nvl(request.getParameter("resetPw"));
        log.info("resetPw : " + resetPw);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserEmail(resetCode);
        pDTO.setUserPw(EncryptUtil.encHashSHA256(resetPw));

        int res = userSevice.resetPw(pDTO);

        if(res == 1) {
            session.invalidate();
        }

        return Integer.toString(res);
    }


}
