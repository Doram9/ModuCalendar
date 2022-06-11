package kopo.poly.dto;

import lombok.Data;

@Data
public class ChatDTO {

    private String prjCode ; // 방번호
    private String userId ; // 유저번호
    private String userName ; // 유저이름
    private String msg ; // 메세지
    private String dateTime ; // 전송시간

}
