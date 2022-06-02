package kopo.poly.controller;


import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.DustInfoDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IAppoService;
import kopo.poly.service.IEventService;
import kopo.poly.service.IUserService;
import kopo.poly.service.IWeatherService;
import kopo.poly.service.impl.AppoService;
import kopo.poly.service.impl.WeatherService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import kopo.poly.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Slf4j
@Controller
public class AppointmentController {

    @Resource(name = "WeatherService")
    private IWeatherService weatherService;

    @Resource(name = "AppoService")
    private IAppoService appoService;

    @Resource(name = "UserService")
    private IUserService userSevice;

    //약속방 페이지
    @GetMapping(value = "appo")
    public String appoPage(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("controller.appoPage start");

        String appoCode = CmmUtil.nvl(request.getParameter("code"));
        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        String title = CmmUtil.nvl(request.getParameter("title"));

        HashMap<String, Object> pMap = new HashMap<>();

        pMap.put("userId", userId);
        pMap.put("title", title);
        pMap.put("appoCode", appoCode);

        AppoInfoDTO rDTO = appoService.getAppoInfo(pMap);

        if(rDTO == null) {
            return "/";
        } else {
            String region = rDTO.getRegion();

            UserInfoDTO pDTO = userSevice.getUserInfo(userId);

            //미세먼지 정보 가져오기
            DustInfoDTO wDTO = weatherService.getDustInfo(region);

            model.addAttribute("UserInfoDTO", pDTO);
            model.addAttribute("AppoInfoDTO", rDTO);
            model.addAttribute("DustInfoDTO", wDTO);

            return "/mocal/Appo";
        }

    }

    //약속방 만들기
    @GetMapping(value = "createAppo")
    public String createAppo(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.createAppo start");

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        String userName = CmmUtil.nvl((String)session.getAttribute("userName"));;

        String title = request.getParameter("title");
        String region = request.getParameter("region");
        String yyyymm = CmmUtil.nvl(request.getParameter("month"));
        int voteday = Integer.parseInt(request.getParameter("deadline"));
        String deadline = DateUtil.addDate(voteday);

        //방 생성시각
        String date = DateUtil.getDateTime("YYYYMMddHHmmss");

        log.info("date : " + date);
        String appoCode = EncryptUtil.encHashSHA256(userId + date);

        AppoInfoDTO pDTO = new AppoInfoDTO();
        pDTO.setAppoCode(appoCode);
        pDTO.setTitle(title);
        pDTO.setRegion(region);
        pDTO.setYyyymm(yyyymm);
        pDTO.setDeadline(deadline);

        UserInfoDTO uDTO = new UserInfoDTO();
        uDTO.setUserId(userId);
        uDTO.setUserName(userName);

        int res = appoService.createAppo(pDTO, uDTO);


        return "redirect:/";
    }

    //약속방 나가기
    @GetMapping(value = "delAppo")
    @ResponseBody
    public String delAppo(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.delAppo start");
        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        String appoCode = CmmUtil.nvl(request.getParameter("code"));
        String title = CmmUtil.nvl(request.getParameter("title"));

        HashMap<String, Object> pMap = new HashMap<>();

        pMap.put("userId", userId);
        pMap.put("title", title);
        pMap.put("appoCode", appoCode);

        int res = appoService.deleteAppo(pMap);

        return Integer.toString(res);
    }

    //초대코드입력 페이지
    @GetMapping(value = "inviteAppo")
    @ResponseBody
    public String inviteAppo(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.inviteAppo start");

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        String userName = CmmUtil.nvl((String)session.getAttribute("userName"));

        String appoCode = CmmUtil.nvl(request.getParameter("appoCode"));

        HashMap<String, Object> pMap = new HashMap<>();

        pMap.put("userId", userId);
        pMap.put("userName", userName);
        pMap.put("appoCode", appoCode);

        int res = appoService.inviteAppo(pMap);

        return Integer.toString(res);
    }

    //투표하기
    @GetMapping(value = "voteDate")
    @ResponseBody
    public String voteDate(HttpServletRequest request, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + "voteDate Start!");


        String voteInfo = request.getParameter("voteInfo");
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject)parser.parse(voteInfo);

        HashMap<String, Object> pMap = new HashMap<>();

        pMap.put("posdays", jsonObject.get("posdays"));
        pMap.put("negdays", jsonObject.get("negdays"));
        pMap.put("appoCode", jsonObject.get("appoCode"));

        pMap.put("userId", session.getAttribute("userId"));

        int res = appoService.voteAppo(pMap);

        return Integer.toString(res);
    }
}
