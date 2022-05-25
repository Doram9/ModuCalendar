package kopo.poly.controller;


import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class AppointmentController {

    //약속방 페이지
    @GetMapping(value = "appo")
    public String appoPage() throws Exception {
        log.info("controller.title start");
        return "/mocal/Appo";
    }


    //약속방 만들기
    @GetMapping(value = "createAppo")
    public String createAppo(HttpRequest request, HttpSession session) throws Exception {
        log.info("controller.createAppo start");

        String userid = session.getAttribute("userId").toString();
        String username = session.getAttribute("userName").toString();

        //방 생성시각
        String date = DateUtil.getDateTime("YYYYMMddHHmmss");

        log.info("date : " + date);
        String appoCode = EncryptUtil.encHashSHA256(userid + date);

        log.info("appoCode : " + appoCode);



        return "/index";
    }

    //초대코드입력 페이지
}
