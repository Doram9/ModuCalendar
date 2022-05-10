package kopo.poly.controller;


import kopo.poly.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.print.attribute.Attribute;

@Slf4j
@Controller
public class UserController {

    public UserRepository userRepository;


    //로그인페이지
    @GetMapping(value = "login")
    public String loginPage() throws Exception {
        log.info("controller.title start");
        return "login";
    }

    //로그인 정보
    @GetMapping(value = "login")
    public String loginSql(RequestParam request) throws Exception {
        log.info("controller.title start");
        return "";
    }

    //회원가입페이지
    @GetMapping(value = "register")
    public String regPage() throws Exception {
        log.info("controller.title start");
        return "register";
    }

    //회원가입 정보

    //아이디찾기 페이지
    @GetMapping(value = "findId")
    public String findIdPage() throws Exception {
        log.info("controller.title start");
        return "findId";
    }

    //아이디찾기 정보

    //비밀번호찾기 페이지
    @GetMapping(value = "findPw")
    public String findPwPage() throws Exception {
        log.info("controller.title start");
        return "findPw";
    }

    //비밀번호찾기 정보

    //유저 기본 페이지
    @GetMapping(value = "index")
    public String mainPage() throws Exception {
        log.info("controller.title start");
        return "index";
    }

    //유저 정보 페이지
}
