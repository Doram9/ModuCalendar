package kopo.poly.controller;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TeamPrjController {

    //팀프로젝트방 페이지
    @GetMapping(value = "teamPrj")
    public String teamPrjPage() throws Exception {
        log.info("controller.title start");
        return "teamPrj";
    }
    //초대코드입력 페이지

    //마일스톤수정 페이지
    @GetMapping(value = "mile")
    public String milePage() throws Exception {
        log.info("controller.title start");
        return "mile";
    }
    //단체채팅 페이지
}
