package com.example.moira.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/test")
public class TestController {

    @RequestMapping(value = "/main")
    public String test() {
        return "HelloWorld";
    }
}
