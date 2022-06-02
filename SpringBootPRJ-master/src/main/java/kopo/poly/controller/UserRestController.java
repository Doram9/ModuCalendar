package kopo.poly.controller;


import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.MailUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.jasper.tagplugins.jstl.core.Url;
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
            session.setAttribute("userName", pDTO.getUserName());
            session.setAttribute("regDt", pDTO.getRegDt());
            session.setAttribute("userEmail", pDTO.getUserEmail());
            session.setAttribute("appoList", pDTO.getAppoList());
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
    @GetMapping(value = "doResetPw")
    public String doResetPw(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller.doResetPw start");

        String resetCode = CmmUtil.nvl(request.getParameter("resetCode"));
        log.info("resetCode : " + resetCode);
        String resetPw = CmmUtil.nvl(request.getParameter("resetPw"));
        log.info("resetPw : " + resetPw);

        UserInfoDTO pDTO = new UserInfoDTO();
        pDTO.setUserId(resetCode);
        pDTO.setUserPw(EncryptUtil.encHashSHA256(resetPw));

        int res = userSevice.resetPw(pDTO);

        if(res == 1) {
            session.invalidate();
        }

        return Integer.toString(res);
    }

    //회원탈퇴
    @GetMapping(value = "deleteUser")
    public String deleteUser(HttpServletRequest request, HttpSession session) throws Exception {

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

        return msg;
    }

}
