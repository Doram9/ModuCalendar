package kopo.poly.dto;

import lombok.Data;

import java.util.List;

@Data
public class VoteInfoDTO {
	private String userid;
	private String username;
	private boolean votetf;
	private List<String> posday;
	private List<String> negday;
}
