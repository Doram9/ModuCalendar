package kopo.poly.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude
public class EventDTO {

    private String event_id; //유저id_생성시간
    private String title;
    private String start;
    private String end;

}
