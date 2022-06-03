package kopo.poly.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrjInfoDTO {

//    팀프로젝트dto
//    프로젝트 코드(제목 + 생성시간)
    private String prjCode;
//    프로젝트 제목
    private String prjTitle;
//    프로젝트 생성시간
    private String prjRegDt;
//    프로젝트 시작일
    private String prjStartDate;
//    프로젝트 마감일
    private String prjEndDate;
//    프로젝트 멤버
    private List<PlayerInfo> prjPlayer;
//    프로젝트 마일스톤
    private MileDTO prjMileInfo;
}
