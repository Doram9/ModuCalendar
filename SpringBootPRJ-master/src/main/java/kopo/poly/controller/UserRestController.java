package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.INoticeService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
            session.setAttribute("userName", pDTO.getUserName());
            session.setAttribute("regDt", pDTO.getRegDt());
            session.setAttribute("userEmail", pDTO.getUserEmail());
            session.setAttribute("appList", pDTO.getAppList());
            session.setAttribute("prjList", pDTO.getPrjList());

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
