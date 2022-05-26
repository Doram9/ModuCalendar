package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Map;

@Slf4j
@Controller
public class UserController {

    @Resource(name = "UserService")
    private IUserService userSevice;


    //로그인페이지
    @GetMapping(value = "login")
    public String loginPage() throws Exception {
        log.info("controller.title start");

        return "/mocal/Login";
    }

    //로그아웃
    @GetMapping(value = "logout")
    public String logout(HttpSession session) throws Exception {
        log.info("controller.logout start");

        session.invalidate();

        return "redirect:/";
    }

    //회원가입페이지
    @GetMapping(value = "register")
    public String regPage() throws Exception {
        log.info("controller.register start");
        return "/mocal/Register";
    }

    //회원가입 정보
    @PostMapping(value = "doRegister")
    public String register(HttpServletRequest request) throws Exception {
        log.info("controller.register start");

        String regName = CmmUtil.nvl(request.getParameter("inputName")); //이름
        String regEmail = CmmUtil.nvl(request.getParameter("inputEmail")); //이메일
        String regId = CmmUtil.nvl(request.getParameter("inputId")); //아이디
        String regPw = CmmUtil.nvl(request.getParameter("inputPassword")); //비밀번호
        regPw = EncryptUtil.encHashSHA256(regPw); //암호화된 비밀번호

        UserInfoDTO pDTO = new UserInfoDTO();

        pDTO.setUserId(regId);
        pDTO.setUserName(regName);
        pDTO.setUserEmail(regEmail);
        pDTO.setUserPw(regPw);
        pDTO.setRegDt(DateUtil.getDateTimeHMS());
        pDTO.setUpdateDt(DateUtil.getDateTimeHMS());
        pDTO.setAppList(new ArrayList<>());
        pDTO.setPrjList(new ArrayList<>());

        int res = userSevice.regUser(pDTO);
        log.info("controller.register result : " + res);
        return "redirect:/login";
    }
//
//    //회원가입 정보
//
//    //아이디찾기 페이지
//    @GetMapping(value = "findId")
//    public String findIdPage() throws Exception {
//        log.info("controller.title start");
//        return "findId";
//    }
//
//    //아이디찾기 정보
//
    //비밀번호찾기 페이지
    @GetMapping(value = "findPw")
    public String findPw() throws Exception {
        log.info("controller.findPw start");
        return "/mocal/Password";
    }

    //비밀번호 재설정 페이지
    @GetMapping(value = "resetPw")
    public String resetPw(HttpServletRequest request) throws Exception {
        log.info("controller.resetPw start");

        if(CmmUtil.nvl(request.getParameter("code")) == "") {
            return "/";
        }

        return "/mocal/resetPassword";
    }

    //유저 기본 페이지
    @GetMapping(value = "index")
    public String mainPage() throws Exception {
        log.info("controller.mainPage start");
        return "index";
    }

    //유저 정보 페이지

    //유저, 팀 마일스톤 수정페이지
    @GetMapping(value = "mile")
    public String mile() throws Exception {
        log.info("controller.mile start");
        return "/mocal/Mile";
    }
}
