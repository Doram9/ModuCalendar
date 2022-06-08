package kopo.poly.controller;


import kopo.poly.dto.*;
import kopo.poly.service.IPrjService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
public class PrjController {
    @Resource(name = "UserService")
    private IUserService userSevice;
    @Resource(name = "PrjService")
    private IPrjService prjService;

    //팀프로젝트방 페이지
    @GetMapping(value = "prj")
    public String prjPage(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("controller.prjPage start");
        String prjCode = request.getParameter("code");
        log.info("prjCode : "  + prjCode);
        PrjInfoDTO pDTO = new PrjInfoDTO();
        pDTO.setPrjCode(prjCode);

        PrjInfoDTO rDTO = prjService.getPrjInfo(pDTO);

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));


        if(rDTO == null) {
            return "/";
        } else {
            UserInfoDTO uDTO = userSevice.getUserInfo(userId);

            model.addAttribute("UserInfoDTO", uDTO);
            model.addAttribute("PrjInfoDTO", rDTO);

            return "/mocal/Prj";
        }
    }
    //팀프로젝트 생성
    @GetMapping(value = "createPrj")
    public String createPrj(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.createPrj start");
        String userId = (String) session.getAttribute("userId");

        String title = CmmUtil.nvl(request.getParameter("title"));
        log.info("title : " + title);
        String startPrjDate = CmmUtil.nvl(request.getParameter("startDate"));
        log.info("startPrjDate : " + startPrjDate);
        String endPrjDate = CmmUtil.nvl(request.getParameter("endDate"));
        log.info("endPrjDate : " + endPrjDate);
        String regDt = DateUtil.getDateTime("YYYYMMddHHmmss");
        log.info("regDt : " + regDt);
        String prjCode = EncryptUtil.encHashSHA256(userId + regDt);

        PrjInfoDTO pDTO = new PrjInfoDTO();
        pDTO.setPrjCode(prjCode);
        pDTO.setPrjTitle(title);
        pDTO.setPrjStartDate(startPrjDate);
        pDTO.setPrjEndDate(endPrjDate);
        pDTO.setPrjRegDt(regDt);

        MileDTO mDTO = new MileDTO();
        mDTO.setItemValue(title);
        mDTO.setItemStartDate(startPrjDate);
        mDTO.setItemEndDate(endPrjDate);
        List<MileDTO> mList = new ArrayList<>();
        mList.add(mDTO);
        mDTO = null;
        pDTO.setPrjMileInfo(mList);
        mList = null;

        PlayerInfoDTO lDTO = new PlayerInfoDTO();
        lDTO.setUserId(userId);
        lDTO.setUserName((String) session.getAttribute("userName"));
        lDTO.setUserRole("팀장");
        lDTO.setUserGrant("master");
        List<PlayerInfoDTO> pList = new ArrayList<>();
        pList.add(lDTO);
        lDTO = null;
        pDTO.setPrjPlayer(pList);
        pList = null;

        int res = prjService.createPrj(pDTO, userId);


        return "redirect:/";
    }
    //초대코드입력 페이지



    //단체채팅 페이지

    //마일스톤수정페이지
    @GetMapping(value = "mile")
    public String updateMile(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("controller.updateMile start");

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        String prjCode = request.getParameter("prjCode");
        log.info("prjCode : " + prjCode);
        PrjInfoDTO pDTO = new PrjInfoDTO();
        pDTO.setPrjCode(prjCode);
        PrjInfoDTO rDTO = prjService.getPrjInfo(pDTO);

        if(rDTO == null) {
            return "/";
        } else {
            UserInfoDTO uDTO = userSevice.getUserInfo(userId);

            model.addAttribute("UserInfoDTO", uDTO);
            model.addAttribute("PrjInfoDTO", rDTO);

            return "/mocal/Mile";
        }
    }

    //마일스톤수정ajax
    @GetMapping(value = "updateMile")
    @ResponseBody
    public String updateMileForAjax(HttpServletRequest request) throws Exception {
        log.info("controller.updateMileForAjax start");

        String prjCode = request.getParameter("prjCode");
        log.info("prjCode : " + prjCode);
        String prjStartDate = request.getParameter("prjStartDate");
        log.info("prjStartDate : " + prjStartDate);
        String prjEndDate = request.getParameter("prjEndDate");
        log.info("prjEndDate : " + prjEndDate);
        String mileInfo = request.getParameter("mileInfo");
        log.info("mileInfo : " + mileInfo);

        JSONParser parser = new JSONParser();
        JSONArray jsonArray = (JSONArray)parser.parse(mileInfo);

        //변경할 prj정보DTO
        PrjInfoDTO pDTO = new PrjInfoDTO();
        pDTO.setPrjStartDate(prjStartDate); //시작일
        pDTO.setPrjEndDate(prjEndDate); //종료일
        pDTO.setPrjCode(prjCode); //프로젝트코드

        List<MileDTO> mList = new ArrayList<>();
        log.info("수정할 마일스톤 아이템 for문 시작");
        for(int i = 0; i < jsonArray.size(); i++) {
            JSONObject item = (JSONObject) jsonArray.get(i);
            MileDTO mDTO = new MileDTO();
            mDTO.setItemValue((String)item.get("itemValue"));
            log.info("itemValue : " + item.get("itemValue"));

            mDTO.setItemStartDate((String)item.get("itemStartDate"));
            log.info("itemStartDate : " + item.get("itemStartDate"));

            mDTO.setItemEndDate((String)item.get("itemEndDate"));
            log.info("itemEndDate : " + item.get("itemEndDate"));
            mList.add(mDTO);
            mDTO = null;
        }
        pDTO.setPrjMileInfo(mList);

        int res = prjService.updateMile(pDTO);


        return Integer.toString(res);
    }

    //마일스톤 삭제
    @GetMapping(value = "deleteMile")
    @ResponseBody
    public String deleteMile(HttpServletRequest request) throws Exception {
        log.info("controller.deleteMile start");

        String prjCode = request.getParameter("prjCode");
        log.info("prjCode : " + prjCode);
        String prjStartDate = request.getParameter("prjStartDate");
        log.info("prjStartDate : " + prjStartDate);
        String prjEndDate = request.getParameter("prjEndDate");
        log.info("prjEndDate : " + prjEndDate);
        String prjTitle = request.getParameter("prjTitle");
        log.info("prjTitle : " + prjTitle);

        List<MileDTO> mList = new ArrayList<>();
        MileDTO mDTO = new MileDTO();
        mDTO.setItemValue(prjTitle);
        mDTO.setItemStartDate(prjStartDate);
        mDTO.setItemEndDate(prjEndDate);
        mList.add(mDTO);

        PrjInfoDTO pDTO = new PrjInfoDTO();
        pDTO.setPrjCode(prjCode);
        pDTO.setPrjMileInfo(mList);

        int res = prjService.deleteMile(pDTO);

        return Integer.toString(res);
    }

    //팀원 정보 수정
    @GetMapping(value = "chgPlayerInfo")
    @ResponseBody
    public String chgPlayerInfo(HttpServletRequest request) throws Exception {
        log.info("controller.chgPlayerInfo start");

        String prjCode = request.getParameter("prjCode");
        log.info("prjCode : " + prjCode);
        String userId = request.getParameter("userId");
        log.info("userId : " + userId);
        String userName = request.getParameter("userName");
        log.info("userName : " + userName);
        String userGrant = request.getParameter("userGrant");
        log.info("userGrant : " + userGrant);
        String userRole = request.getParameter("userRole");
        log.info("userRole : " + userRole);

        PrjInfoDTO jDTO = new PrjInfoDTO();
        jDTO.setPrjCode(prjCode);

        PlayerInfoDTO pDTO = new PlayerInfoDTO();
        pDTO.setUserId(userId);
        pDTO.setUserName(userName);
        pDTO.setUserGrant(userGrant);
        pDTO.setUserRole(userRole);

        int res = prjService.updatePlayerInfo(jDTO, pDTO);

        return Integer.toString(res);
    }
}
