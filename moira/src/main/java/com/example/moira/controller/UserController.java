package com.example.moira.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Slf4j
@Controller
public class UserController {

    @Resource(name = "UserService")
    private IUserService userSevice;


    //로그인페이지
    @GetMapping(value = "login")
    public String loginPage() throws Exception {
        log.info("controller.title start");

        String decCode = EncryptUtil.decAES128CBC("2BN6dCntkL81rxX3hewe+daoXvgu0+iCcFwy0Osymx4=");
        log.info(decCode);

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
        pDTO.setAppoList(new ArrayList<>());
        pDTO.setPrjList(new ArrayList<>());
        pDTO.setEventList(new ArrayList<>());

        int res = userSevice.regUser(pDTO);
        return "redirect:/login";
    }

    //비밀번호찾기 페이지
    @GetMapping(value = "findPw")
    public String findPw() throws Exception {
        log.info("controller.findPw start");
        return "/mocal/Password";
    }

    @GetMapping(value = "sendEmail")
    public String sendEmail(HttpServletRequest request, ModelMap model) throws Exception {
        log.info("controller.sendEmail start");

        String userEmail = request.getParameter("email");
        log.info(userEmail);

        userSevice.sendMail(userEmail);

        String msg = "비밀번호 재설정 메일을 발송하였습니다. 이메일을 확인해주세요.";

        model.addAttribute("msg", msg);

        return "/mocal/Msg";
    }

    //비밀번호 재설정 페이지
    @GetMapping(value = "resetPw")
    public String resetPw(HttpServletRequest request, ModelMap model) throws Exception {
        log.info("controller.resetPw start");
        String resetCode = request.getParameter("resetCode");
        log.info("resetCode : " + resetCode);

        model.addAttribute("resetCode", resetCode);
        return "/mocal/resetPassword";
    }

    //유저 기본 페이지
    @GetMapping(value = "/")
    public String mainPage(HttpSession session, ModelMap model) throws Exception {
        log.info("controller.mainPage start");

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));

        UserInfoDTO pDTO = userSevice.getUserInfo(userId);
        model.addAttribute("UserInfoDTO", pDTO);

        return "index";
    }

    //회원탈퇴
    @GetMapping(value = "deleteUser")
    public String deleteUser(HttpSession session, ModelMap model) throws Exception {

        log.info("controller.deleteUser start");

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        log.info(userId);
        UserInfoDTO rDTO = userSevice.getUserInfo(userId);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(userId);
        pDTO.setAppoList(rDTO.getAppoList());
        pDTO.setPrjList(rDTO.getPrjList());

        String msg = "";

        int res = userSevice.deleteUser(pDTO);
        if(res == 1) {
            session.invalidate();
            msg = "정상적으로 회원탈퇴가 진행되었습니다. 이용해주셔서 감사합니다.";
        } else {
            msg = "회원탈퇴 과정에서 에러가 발생했습니다.";
        }
        model.addAttribute("msg", msg);

        return "/mocal/Msg";
    }

}
