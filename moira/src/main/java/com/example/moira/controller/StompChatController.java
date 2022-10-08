package com.example.moira.controller;

import kopo.poly.dto.ChatMessageDTO;
import kopo.poly.service.IChatService;
import kopo.poly.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Controller
@RequiredArgsConstructor
public class StompChatController {

    @Resource(name = "ChatService")
    private IChatService chatService;

    private final SimpMessagingTemplate template; //특정 Broker로 메세지를 전달

    private Map<String, Set<String>> PrjChatList;

    @PostConstruct
    private void init(){
        PrjChatList = new HashMap<>();
    }

    //Client가 SEND할 수 있는 경로
    //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    //"/pub/chat/enter"
    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message) throws Exception {
        String prjCode = message.getPrjCode();

        //해당 프로젝트 채팅에 처음 접속하면
        if(PrjChatList.getOrDefault(prjCode, null) == null) {
            Set<String> userList = new HashSet<>();
            userList.add(message.getUserId());
            log.info("userList : " + userList);
            PrjChatList.put(prjCode, userList);
            // 이미 해당 프로젝트 채팅방에 누군가 접속해있다면
        } else {
            Set<String> userList = PrjChatList.get(prjCode);
            userList.add(message.getUserId());
            log.info("userList : " + userList);
            PrjChatList.put(prjCode, userList);
        }
    }

    @MessageMapping(value = "/chat/exit")
    public void exit(ChatMessageDTO message) throws Exception {

        String prjCode = message.getPrjCode();
        Set<String> userList = PrjChatList.get(prjCode);
        userList.remove(message.getUserId());
        log.info("userList : " + userList);
        //채팅방에 아무도 접속해있지 않다면
        if(userList.size() == 0) { //유저가 없으면
            log.info("채팅방 폐쇄");
            chatService.saveMessage(message);
        }
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message) throws Exception {
        log.info("발신아이디 : " + message.getUserId());
        message.setSendTime(DateUtil.getDateTimeHMS());
        template.convertAndSend("/sub/chat/room/" + message.getPrjCode(), message);
        //redis에 채팅로그 저장
        chatService.addMessage(message);
    }
}