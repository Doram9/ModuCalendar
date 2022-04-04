package kopo.poly.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.JsonParser;

import kopo.poly.dto.UserInfoDTO;
import kopo.poly.dto.VoteInfoDTO;
import kopo.poly.persistance.mongo.comm.IUserMapper;
import kopo.poly.persistance.mongo.impl.UserMapper;
import kopo.poly.service.IMongoUserService;
import kopo.poly.util.DateUtil;


@Service("MongoUserService")
public class MongoUserService implements IMongoUserService {
	
	@Resource(name="UserMapper")
	private IUserMapper userMapper;

	
	final String colnm = "User";
	
	@Override
	public UserInfoDTO getUserInfo(String userid) throws Exception {

		
		
		UserInfoDTO rDTO = new UserInfoDTO();
		
		rDTO = userMapper.getUserInfo(colnm, userid);
		
		
		
		return rDTO;
	}
	
	
	@Override
	public void insertUser(HashMap<String, Object> kakaoUserInfo) throws Exception {

		
		Map<String, Object> pMap = new HashMap<>();
		List<String> roomcode = new ArrayList<>();
		
		pMap.put("_id", (String) kakaoUserInfo.get("id"));
		pMap.put("username", (String) kakaoUserInfo.get("nickname"));
		pMap.put("userbirth", (String) kakaoUserInfo.get("userbirth"));
		pMap.put("regdt", DateUtil.getDateTime());
		pMap.put("mkcnt", 0);
		pMap.put("roomcode", roomcode);
		
		
		userMapper.insertUser(pMap, colnm);
		
	}
	
	@Override
	public void addEventService(HashMap<String ,Object> pMap) throws Exception {

		
		int res = userMapper.addEventUserInfo(pMap);
		
	}
	
	@Override
	public void delEventService(HashMap<String ,Object> pMap) throws Exception {

		
		int res = userMapper.delEventUserInfo(pMap);
		
	}
	
	@Override
	public void inviteRoom(HashMap<String, Object> pMap) throws Exception {

		
		String userid = (String) pMap.get("userid");
		String roomcode = (String) pMap.get("roomcode");
		String title = (String) pMap.get("title");
		
		VoteInfoDTO pDTO = new VoteInfoDTO();
		pDTO.setNegday(null);
		pDTO.setPosday(null);
		pDTO.setUserid(userid);
		pDTO.setVotetf(false);
		
		userMapper.inviteUser(pDTO, pMap);
		
	}
}
