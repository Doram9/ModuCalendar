package com.example.moira.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    //타이틀 페이지
    @GetMapping(value = "title")
    public String titlePage() throws Exception {
            log.info("controller.title start");
        return "/mocal/title";
    }


}
