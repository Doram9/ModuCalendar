package kopo.poly.dto;


import lombok.Data;

@Data
public class PlayerInfo {
    private String userId;
    private String userName;
    //팀원 역할(아무거나 기재가능)
    private String userRole;
    //팀원 권한(master=팀장, senior=마일스톤수정가능팀원, junior=마일스톤수정불가능팀원)
    private String userGrant;
}
