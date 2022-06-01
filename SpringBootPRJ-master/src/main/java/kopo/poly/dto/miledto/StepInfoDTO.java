package kopo.poly.dto.miledto;


import lombok.Data;

import java.util.List;

@Data
public class StepInfoDTO {
    private String value;
    private List<ItemInfoDTO> item_info;
}
