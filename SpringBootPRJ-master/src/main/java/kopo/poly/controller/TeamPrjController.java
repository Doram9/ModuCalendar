package kopo.poly.controller;


import kopo.poly.dto.miledto.AllInfoDTO;
import kopo.poly.dto.miledto.MileInfoDTO;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

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



    //단체채팅 페이지

    //마일스톤수정
    @GetMapping(value = "updateMile")
    public String updateMile(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.updateMile start");

        String mileInfo = request.getParameter("mileInfo");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(mileInfo);

        HashMap<String, Object> pMap = new HashMap<>();

        //프로젝트 시작일
        pMap.put("posdays", jsonObject.get("posdays"));
        //프로젝트 마감일
        pMap.put("endDate", jsonObject.get("endDate"));
        //mileInfo : 단계 배열
        pMap.put("mileInfo", jsonObject.get("mileInfo"));
        //단계 세부항목(단계명, 항목정보, 마일스톤정보)
        pMap.put("step_info", jsonObject.get("step_info"));
        //단계명
        pMap.put("value", jsonObject.get("value"));
        //항목정보
        pMap.put("item_info", jsonObject.get("item_info"));
        //항목명 배열
        pMap.put("itemValue", jsonObject.get("itemValue"));
        //항목의 마일스톤on_off정보
        pMap.put("mileTF", jsonObject.get("mileTF"));

        return "";
    }
}
