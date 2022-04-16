package kopo.poly.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class MainController {

    //타이틀 페이지
    @GetMapping(value = "mocal/title")
    public String titlePage() throws Exception {
            log.info("controller.title start");
        return "";
    }

    //로그인페이지

    //회원가입페이지

    //아이디찾기 페이지

    //비밀번호찾기 페이지






}
