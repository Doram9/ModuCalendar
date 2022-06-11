package kopo.poly.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
@Slf4j
public class ChatController {

    @GetMapping("/chat")
    public String chatGET(HttpSession session, ModelMap model){
        log.info("@ChatController, chat GET()");

        String userId = (String) session.getAttribute("userId");
        log.info("userId :" + userId);

        model.addAttribute("userId", userId);

        return "/mocal/Chat";
    }
}
