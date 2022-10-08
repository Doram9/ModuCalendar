package com.example.moira.redis.impl;


import kopo.poly.dto.DustInfoDTO;
import kopo.poly.persistance.redis.IWeatherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component("WeatherMapper")
public class WeatherMapper implements IWeatherMapper {

    public final RedisTemplate<String, Object> redisDB;

    public WeatherMapper(RedisTemplate<String, Object> redisDB) {
        this.redisDB = redisDB;
    }


    @Override
    public DustInfoDTO getDustInfo(String redisKey) throws Exception {
        log.info("mapper.getDustInfo start");
        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(DustInfoDTO.class));

        DustInfoDTO rDTO = null;
        if(redisDB.hasKey(redisKey)) {
            List<DustInfoDTO> rList = (List) redisDB.opsForList().range(redisKey, 0, -1);
            rDTO = rList.get(0);

        }


        return rDTO;
    }

    @Override
    public DustInfoDTO insertDustInfo(DustInfoDTO pDTO) throws Exception {
        log.info("mapper.insertDustInfo start");
        redisDB.setKeySerializer(new StringRedisSerializer());
        redisDB.setValueSerializer(new Jackson2JsonRedisSerializer<>(DustInfoDTO.class));

        String redisKey = pDTO.getPresnatnDt();

        redisDB.opsForList().rightPush(redisKey, pDTO);

        redisDB.expire(redisKey, 1, TimeUnit.DAYS);

        List<DustInfoDTO> rList = (List) redisDB.opsForList().range(redisKey, 0, -1);
        DustInfoDTO rDTO = rList.get(0);

        return rDTO;
    }
}
