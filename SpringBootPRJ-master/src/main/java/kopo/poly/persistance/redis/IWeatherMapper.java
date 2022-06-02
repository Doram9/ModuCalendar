package kopo.poly.persistance.redis;

import kopo.poly.dto.DustInfoDTO;

public interface IWeatherMapper {

    DustInfoDTO getDustInfo(String redisKey) throws Exception;

    DustInfoDTO insertDustInfo(DustInfoDTO pDTO) throws Exception;
}
