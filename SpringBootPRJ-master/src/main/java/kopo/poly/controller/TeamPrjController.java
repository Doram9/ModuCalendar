package kopo.poly.controller;


import kopo.poly.dto.miledto.AllInfoDTO;
import kopo.poly.dto.miledto.MileInfoDTO;
import kopo.poly.service.ITeamPrjService;
import kopo.poly.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

@Slf4j
@RestController
public class TeamPrjController {

    @Resource(name = "TeamPrjService")
    private ITeamPrjService teamPrjService;

    //팀프로젝트방 페이지
    @GetMapping(value = "teamPrj")
    public String teamPrjPage() throws Exception {
        log.info("controller.title start");
        return "teamPrj";
    }
    //초대코드입력 페이지



    //단체채팅 페이지

    //마일스톤수정
    @GetMapping(value = "updateMile")
    public String updateMile(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.updateMile start");

        String allInfo = request.getParameter("allInfo");
        String prjCode = request.getParameter("prjCode");

        HashMap<String, Object> pMap = new HashMap<>();
        pMap.put("prjCode", prjCode);
        pMap.put("allInfo", allInfo);

        int res = teamPrjService.updateMile(pMap);
        return Integer.toString(res);
    }
}
