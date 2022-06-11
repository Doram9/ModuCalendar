package kopo.poly.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Data
public class ChatRoomDTO {

    private String prjCode;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDTO create(String prjCode) {
        ChatRoomDTO room = new ChatRoomDTO();
        room.prjCode = prjCode;
        return room;
    }
}
