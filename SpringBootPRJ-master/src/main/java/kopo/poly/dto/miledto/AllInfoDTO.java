package kopo.poly.dto.miledto;

import lombok.Data;

import java.util.List;

@Data
public class AllInfoDTO {

    private String prjCode;
    private String startDate;
    private String endDate;
    private List<MileInfoDTO> mileInfo;
}
