package kopo.poly.service.impl;

import kopo.poly.service.ITeamPrjService;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Slf4j
@Service("TeamPrjService")
public class TeamPrjService implements ITeamPrjService {


    @Override
    public int updateMile(HashMap<String, Object> pMap) throws Exception {

        //마일스톤 총 정보
        String allInfo = (String)pMap.get("allInfo");
        String prjCode = (String)pMap.get("prjCode");

        JSONParser parser = new JSONParser();
        //마일스톤 총정보 json으로 전체 파싱
        JSONObject jsonObject = (JSONObject)parser.parse(allInfo);

        JSONArray mileInfo = (JSONArray) parser.parse("mileInfo");
        for(Object obj : mileInfo) {
            log.info((String) obj);
        }
        return 0;
    }
}
