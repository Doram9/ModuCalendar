package kopo.poly.controller;


import kopo.poly.dto.EventDTO;
import kopo.poly.service.IEventService;
import kopo.poly.service.IUserService;
import kopo.poly.util.CmmUtil;
import kopo.poly.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Slf4j
@Controller
public class EventController {

    @Resource(name = "EventService")
    private IEventService eventSevice;

    //일정 추가
    @GetMapping(value = "addEvent")
    public String addEvent(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller.addEvent start");

        String title = CmmUtil.nvl(request.getParameter("title"));
        String start = CmmUtil.nvl(request.getParameter("start"));
        String end = CmmUtil.nvl(request.getParameter("end"));

        log.info(title);
        log.info(start);

        String time = DateUtil.getDateTime("yyyyMMddHHmmss");
        String userId = CmmUtil.nvl((String) session.getAttribute("userId"));
        String eventId = userId + "_" + time;

        EventDTO pDTO = new EventDTO();
        pDTO.setEvent_id(eventId);
        pDTO.setTitle(title);
        pDTO.setStart(start);
        pDTO.setEnd(end);

        int res = eventSevice.addEvent(userId, pDTO);

        return "";
    }
    //일정 삭제
    @GetMapping(value = "deleteEvent")
    public String deleteEvent(HttpServletRequest request, HttpSession session) throws Exception {

        log.info("controller.addEvent start");

        String eventId = CmmUtil.nvl(request.getParameter("eventId"));
        String userId = CmmUtil.nvl((String) session.getAttribute("userId"));


        int res = eventSevice.deleteEvent(userId, eventId);

        return "";
    }

}
