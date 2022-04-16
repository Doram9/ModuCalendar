package kopo.poly.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AppointmentController {

    //약속방 페이지
    @GetMapping(value = "appo")
    public String appoPage() throws Exception {
        log.info("controller.title start");
        return "appo";
    }

    //초대코드입력 페이지
}
