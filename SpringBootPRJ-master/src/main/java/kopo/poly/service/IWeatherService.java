package kopo.poly.service;

import kopo.poly.dto.DustInfoDTO;

public interface IWeatherService {

    DustInfoDTO getDustInfo(String appKey) throws Exception;
}
