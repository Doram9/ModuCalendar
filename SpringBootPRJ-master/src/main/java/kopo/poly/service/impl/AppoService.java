package kopo.poly.service.impl;

import kopo.poly.dto.AppoInfoDTO;
import kopo.poly.dto.VoteInfoDTO;
import kopo.poly.persistance.redis.IAppoMapper;
import kopo.poly.service.IAppoService;
import kopo.poly.util.EncryptUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service("AppoService")
public class AppoService implements IAppoService {

    @Resource(name="AppoMapper")
    IAppoMapper appoMapper;

    @Override
    public int createAppo(AppoInfoDTO pDTO, String userid, String username) throws Exception {

        int res = 0;

        pDTO.setFirdate("");
        pDTO.setSecdate("");
        pDTO.setThidate("");

        VoteInfoDTO vDTO = new VoteInfoDTO();
        vDTO.setUserid(userid);
        vDTO.setUsername(username);
        vDTO.setVotetf(false);
        vDTO.setPosday(null);
        vDTO.setNegday(null);

        List<VoteInfoDTO> rList = new ArrayList<>();
        rList.add(vDTO);

        pDTO.setUserlist(rList);

        res = appoMapper.createAppo(pDTO, userid);

        return res;
    }

    @Override
    public void deleteAppo(HashMap<String, Object> pMap) throws Exception {

    }

    @Override
    public AppoInfoDTO getAppoInfo(String roomcode) throws Exception {
        return null;
    }
}
