package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.jasper.tagplugins.jstl.core.Url;
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

    //회원가입_이메일중복체크
    @RequestMapping(value = "checkEmail")
    public String checkEmail(@RequestParam("checkEmail") String checkEmail) throws Exception {

        log.info("checkEmail : " + checkEmail);

        int res = userSevice.checkingEmail(checkEmail);

        return "" + res;
    }

    /**
     * 이메일 발송
     */
    //비밀번호찾기 정보
    @PostMapping(value = "doFindPw")
    public String findPwByEmail(HttpServletRequest request) throws Exception {

        log.info("controller.findPwByEmail start");

        String email = CmmUtil.nvl(request.getParameter("email"));

        //int res = userSevice.findPw(email);

        return "비밀번호 재설정 링크를 발송했습니다. 메일을 확인해주세요.";
    }

    //비밀번호 재설정
    @PostMapping(value = "doResetPw")
    public String doResetPw(HttpServletRequest request) throws Exception {

        log.info("controller.doResetPw start");

        String userId = CmmUtil.nvl(request.getParameter("userId"));
        String resetPw = CmmUtil.nvl(request.getParameter("resetPw"));



        return "";
    }


}
