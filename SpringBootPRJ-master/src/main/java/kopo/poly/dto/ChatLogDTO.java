package kopo.poly.dto;

import lombok.Data;

import java.util.List;

@Data
public class ChatLogDTO {

    private String prjCode;

    private List<ChatMessageDTO> chatLog;
}
