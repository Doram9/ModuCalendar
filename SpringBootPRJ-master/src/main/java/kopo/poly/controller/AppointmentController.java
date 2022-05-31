package kopo.poly.controller;


import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.service.IAppoService;
import kopo.poly.service.IEventService;
import kopo.poly.service.IUserService;
import kopo.poly.service.impl.AppoService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Controller
public class AppointmentController {

    @Resource(name = "AppoService")
    private IAppoService appoService;

    @Resource(name = "UserService")
    private IUserService userSevice;

    //약속방 페이지
    @GetMapping(value = "appo")
    public String appoPage(HttpServletRequest request, ModelMap model, HttpSession session) throws Exception {
        log.info("controller.appoPage start");

        String appoCode = CmmUtil.nvl((String)request.getParameter("code"));
        String userId = CmmUtil.nvl((String)session.getAttribute("userId"));
        log.info("userId : " + userId);
        AppoInfoDTO rDTO = appoService.getAppoInfo(appoCode);
        UserInfoDTO pDTO = userSevice.getUserInfo(userId);

        model.addAttribute("UserInfoDTO", pDTO);
        model.addAttribute("AppoInfoDTO", rDTO);

        return "/mocal/Appo";
    }


    //약속방 만들기
    @GetMapping(value = "createAppo")
    public String createAppo(HttpServletRequest request, HttpSession session) throws Exception {
        log.info("controller.createAppo start");

        String userId = session.getAttribute("userId").toString();
        String userName = session.getAttribute("userName").toString();

        String title = request.getParameter("title");
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
        pDTO.setYyyymm(yyyymm);
        pDTO.setDeadline(deadline);

        UserInfoDTO uDTO = new UserInfoDTO();
        uDTO.setUserId(userId);
        uDTO.setUserName(userName);

        int res = appoService.createAppo(pDTO, uDTO);


        return "redirect:/";
    }

    //초대코드입력 페이지

    //투표하기
    @GetMapping(value = "voteDate")
    @ResponseBody
    public String voteDate(@RequestParam(value="posdays[]") List<String> posdays, @RequestParam(value="negdays[]") List<String> negdays, @RequestParam(value="appoCode") String appoCode, HttpSession session) throws Exception {

        log.info(this.getClass().getName() + "voteDate Start!");
        log.info(posdays.get(0));
        log.info(negdays.get(0));
        log.info(appoCode);

        String userId = session.getAttribute("userId").toString();

//        param.put("userId", session.getAttribute("userId"));
//
//        int res = appoService.voteAppo(param);

        String res = "1";

        return res;
    }
}
