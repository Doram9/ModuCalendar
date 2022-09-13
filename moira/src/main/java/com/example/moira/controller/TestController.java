package com.example.moira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/main")
    @ResponseBody
    public String test() {
        return "HelloWorld";
    }
}
