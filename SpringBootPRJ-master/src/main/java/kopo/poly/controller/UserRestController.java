package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.INoticeService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@RestController
public class UserRestController {

    @Resource(name = "UserService")
    private IUserService userSevice;

    //로그인 정보
    @RequestMapping(value = "dologin")
    public String login(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.title start");


        String reqId = CmmUtil.nvl(request.getParameter("id"));
        String reqPw = CmmUtil.nvl(request.getParameter("pw"));
        UserInfoDTO pDTO = userSevice.authLogin(reqId, reqPw);

        if(pDTO == null) {
            return "fail";
        } else {
            session.setAttribute("userId", pDTO.getUserId());
            session.setAttribute("userName", pDTO.getUserName());
            session.setAttribute("regDt", pDTO.getRegDt());
            session.setAttribute("userEmail", pDTO.getUserEmail());
            session.setAttribute("appList", pDTO.getAppList());
            session.setAttribute("prjList", pDTO.getPrjList());

            return "success";
        }

    }

    //회원가입 정보
    @RequestMapping(value = "register")
    public String register() throws Exception {


        return "";
    }


    //아이디찾기 정보
    @RequestMapping(value = "findId")
    public String findIdPage() throws Exception {
        log.info("controller.title start");
        return "findId";
    }



    //비밀번호찾기 정보
    @RequestMapping(value = "findPw")
    public String findPwPage() throws Exception {
        log.info("controller.title start");
        return "findPw";
    }



}
