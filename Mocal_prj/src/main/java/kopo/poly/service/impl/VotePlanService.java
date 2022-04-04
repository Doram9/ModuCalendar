package kopo.poly.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import kopo.poly.persistance.mongo.comm.IUserVoteMapper;
import kopo.poly.persistance.mongo.impl.UserVoteMapper;
import kopo.poly.service.IVotePlanService;


@Service("VotePlanService")
public class VotePlanService implements IVotePlanService {

	
	@Resource(name="UserVoteMapper")
	IUserVoteMapper userVoteMapper = new UserVoteMapper();

	@Override
	public void userVoteService(HashMap<String, Object> pMap) throws Exception {
		

		
		String roomcode = (String) pMap.get("roomcode");
		
		HashMap<String, ArrayList<String>> voteMap = userVoteMapper.userVote(pMap);
		
		List<String> posdays = voteMap.get("posdays");
		List<String> negdays = voteMap.get("negdays");
		
		Collections.sort(posdays);
		Collections.sort(negdays);
		

		
		String firday = "";
		int fircnt = 0;
		String secday = "";
		int seccnt = 0;
		String thiday = "";
		int thicnt = 0;
		
		for(int i = 0; i < posdays.size(); i++) {
			int cnt = Collections.frequency(posdays, posdays.get(i)); //해당 요일이 몇개 들어있는지
			int minusedcnt = cnt - Collections.frequency(negdays, posdays.get(i));
			if(minusedcnt > 0) {
				if(minusedcnt > thicnt) {
					if(minusedcnt > seccnt) {
						if(minusedcnt > fircnt) {
							firday = posdays.get(i);
							fircnt = minusedcnt;
						} else {
							secday = posdays.get(i);
							seccnt = minusedcnt;
						}
					} else {
						thiday = posdays.get(i);
						thicnt = minusedcnt;
					}
				}
			}
			
			i += cnt - 1;
		}
		
		HashMap<String, Object> rMap = new HashMap<>();
		rMap.put("roomcode", roomcode);
		rMap.put("firday", firday);
		rMap.put("secday", secday);
		rMap.put("thiday", thiday);
		
		userVoteMapper.updateResult(rMap);
		
	}

}
