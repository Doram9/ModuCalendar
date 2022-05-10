package kopo.poly.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserRestController {


    //로그인 정보
    @RequestMapping(value = "login")
    public String loginSql(RequestParam request) throws Exception {
            log.info("controller.title start");
        return "";
    }

    //회원가입 정보


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



    //유저 정보 페이지
    @RequestMapping(value = "index")
    public String mainPage() throws Exception {
        log.info("controller.title start");
        return "index";
    }


}
