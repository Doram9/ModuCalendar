package kopo.poly.dto.miledto;

import lombok.Data;

import java.util.List;

@Data
public class ItemInfoDTO {

    private List<Integer> itemNum;
    private List<String> itemValue;
    private List<List<String>> mileTF;
}
