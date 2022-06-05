package kopo.poly.controller;


import kopo.poly.dto.*;
import kopo.poly.service.IPrjService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;

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

        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));


        if(false) {
            return "/";
        } else {
            UserInfoDTO pDTO = userSevice.getUserInfo(userId);

            model.addAttribute("UserInfoDTO", pDTO);

            return "/mocal/Prj";
        }
    }
    //팀프로젝트 생성
    @GetMapping(value = "createPrj")
    public String createPrj(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.createPrj start");
        String userId = (String) session.getAttribute("userId");
        String title = CmmUtil.nvl(request.getParameter("title"));
        String startPrjDate = CmmUtil.nvl(request.getParameter("startDate"));
        String endPrjDate = CmmUtil.nvl(request.getParameter("endDate"));
        String regDt = DateUtil.getDateTime("yyyy-MM-dd");
        String prjCode = title + "*_*" + EncryptUtil.encHashSHA256(regDt);

        PrjInfoDTO pDTO = new PrjInfoDTO();
        pDTO.setPrjCode(prjCode);
        pDTO.setPrjTitle(title);
        pDTO.setPrjStartDate(startPrjDate);
        pDTO.setPrjEndDate(endPrjDate);
        pDTO.setPrjRegDt(regDt);
        MileDTO mDTO = new MileDTO();
        pDTO.setPrjMileInfo(mDTO);
        PlayerInfo lDTO = new PlayerInfo();
        lDTO.setUserId(userId);
        lDTO.setUserName((String) session.getAttribute("userName"));
        lDTO.setUserRole("팀장");
        lDTO.setUserGrant("master");
        List<PlayerInfo> pList = new ArrayList<>();
        pList.add(lDTO);
        pDTO.setPrjPlayer(pList);

        int res = prjService.createPrj(pDTO, userId);


        return "redirect:/";
    }
    //초대코드입력 페이지



    //단체채팅 페이지

    //마일스톤수정
    @GetMapping(value = "mile")
    public String updateMile(HttpServletRequest request, ModelMap model) throws Exception {
        log.info("controller.updateMile start");

        return "/mocal/Mile";
    }
}
