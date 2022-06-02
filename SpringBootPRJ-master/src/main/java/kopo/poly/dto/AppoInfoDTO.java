package kopo.poly.dto;

import lombok.Data;
import java.util.List;

@Data
public class AppoInfoDTO {
	private String appoCode;
	private List<VoteInfoDTO> userlist;
	private String title;
	private String region;
	private String yyyymm;
	private String deadline;
	private String firdate;
	private String secdate;
	private String thidate;
}
