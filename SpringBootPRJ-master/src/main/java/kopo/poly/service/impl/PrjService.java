package kopo.poly.service.impl;

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
}
