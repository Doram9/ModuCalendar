package com.example.moira.service;

import com.example.moira.dto.DustInfoDTO;

public interface IWeatherService {

    DustInfoDTO getDustInfo(String appKey) throws Exception;
}
