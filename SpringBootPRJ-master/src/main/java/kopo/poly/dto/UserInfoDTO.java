package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInfoDTO {

    //유저 아이디
    private String userId;
    //유저 비밀번호
    private String userPw;
    //유저 이메일
    private String userEmail;
    //유저 이름
    private String userName;
    //계정생성일
    private String regDt;
    //계정업데이트일
    private String updateDt;
    //약속방 목록
    private List<String> appList;
    //팀프로젝트방 목록
    private List<String> prjList;
    //일정 목록
    private List<EventDTO> eventList;
}
