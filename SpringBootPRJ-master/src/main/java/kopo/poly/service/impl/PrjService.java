package kopo.poly.service.impl;

import kopo.poly.dto.PlayerInfoDTO;
import kopo.poly.dto.PrjInfoDTO;
import kopo.poly.persistance.mongodb.IEventMapper;
import kopo.poly.persistance.mongodb.IPrjMapper;
import kopo.poly.service.IPrjService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("PrjService")
public class PrjService implements IPrjService {

    @Resource(name="PrjMapper")
    IPrjMapper prjMapper;

    @Override
    public int createPrj(PrjInfoDTO pDTO, String userId) throws Exception {

        int res = prjMapper.createPrj(pDTO, userId);
        return res;
    }

    @Override
    public int updateMile(PrjInfoDTO pDTO) throws Exception {

        int res = prjMapper.updatePrj(pDTO);
        return res;
    }

    @Override
    public int deleteMile(PrjInfoDTO pDTO) throws Exception {
        return prjMapper.deleteMile(pDTO);
    }

    @Override
    public PrjInfoDTO getPrjInfo(PrjInfoDTO pDTO) throws Exception {

        return prjMapper.getPrjInfo(pDTO);
    }

    @Override
    public int updatePlayerInfo(PrjInfoDTO jDTO, PlayerInfoDTO pDTO) throws Exception {


        return prjMapper.updatePlayerInfo(jDTO, pDTO);
    }
}
