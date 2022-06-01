package kopo.poly.service.impl;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.UserInfoDTO;
import kopo.poly.dto.VoteInfoDTO;
import kopo.poly.persistance.redis.IAppoMapper;
import kopo.poly.service.IAppoService;
import kopo.poly.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Slf4j
@Service("AppoService")
public class AppoService implements IAppoService {

    @Resource(name="AppoMapper")
    IAppoMapper appoMapper;

    @Override
    public int createAppo(AppoInfoDTO pDTO, UserInfoDTO uDTO) throws Exception {


        int res = appoMapper.createAppo(pDTO, uDTO);

        return res;
    }

    @Override
    public int deleteAppo(HashMap<String, Object> pMap) throws Exception {

        int res = appoMapper.deleteAppo(pMap);

        return res;
    }

    @Override
    public AppoInfoDTO getAppoInfo(HashMap<String, Object> pMap) throws Exception {

        AppoInfoDTO rDTO = appoMapper.getAppoInfo(pMap);
        return rDTO;
    }

    @Override
    public int voteAppo(HashMap<String, Object> pMap) throws Exception {

        log.info("uservote service start");

        int res = 1;

        String appoCode = (String) pMap.get("appoCode");

        HashMap<String, ArrayList<String>> voteMap = appoMapper.getVoteInfo(pMap);

        if(voteMap != null) {
            List<String> posdays = voteMap.get("posdays");
            List<String> negdays = voteMap.get("negdays");

            Collections.sort(posdays);
            Collections.sort(negdays);

            log.info("불가능한 날들" + negdays);
            log.info("가능한 날들" + posdays);

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
            rMap.put("appoCode", appoCode);
            rMap.put("firday", firday);
            rMap.put("secday", secday);
            rMap.put("thiday", thiday);

            appoMapper.updateResult(rMap);
        } else {
            res = 0;
        }

        return res;
    }

    @Override
    public int inviteAppo(HashMap<String, Object> pMap) throws Exception {
        int res = appoMapper.inviteAppo(pMap);
        return res;
    }
}
