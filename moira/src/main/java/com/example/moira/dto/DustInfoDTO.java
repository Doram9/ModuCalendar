package com.example.moira.dto;

import lombok.Data;

@Data
public class DustInfoDTO {

    //요청일자
    private String presnatnDt;
    //첫번째 날짜
    private String frcstOneDt;
    //첫번째 미세먼지 정보
    private String frcstOneCn;
    //2번째 날짜
    private String frcstTwoDt;
    //2번째 미세먼지 정보
    private String frcstTwoCn;
    //3번째 날짜
    private String frcstThreeDt;
    //3번째 미세먼지 정보
    private String frcstThreeCn;
    //4번째 날짜
    private String frcstFourDt;
    //4번째 미세먼지 정보
    private String frcstFourCn;
}
