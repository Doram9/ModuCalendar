package kopo.poly.service.impl;

import kopo.poly.dto.DustInfoDTO;
import kopo.poly.persistance.mongodb.IEventMapper;
import kopo.poly.persistance.redis.IWeatherMapper;
import kopo.poly.service.IWeatherService;
import kopo.poly.util.DateUtil;
import kopo.poly.util.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service("WeatherService")
public class WeatherService implements IWeatherService {

    @Resource(name="WeatherMapper")
    IWeatherMapper weatherMapper;


    @Override
    public DustInfoDTO getDustInfo(String region) throws Exception {

        //오늘 날짜이자 미세먼지정보 redisKey
        String today = DateUtil.getDateTime("yyyy-MM-dd");

        DustInfoDTO rDTO = weatherMapper.getDustInfo(today);

        if(rDTO == null) {
            String appKey = KeyUtil.getDustKey();


            StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMinuDustWeekFrcstDspth"); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("serviceKey","UTF-8") + "=" + appKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("returnType","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*xml 또는 json*/
            urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*한 페이지 결과 수*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지번호*/
            urlBuilder.append("&" + URLEncoder.encode("searchDate","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*통보시간 검색(조회 날짜 입력이 없을 경우 호출 당일 날짜를 기준으로 주간예보 리스트 확인)*/
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            //log.info("Response code: " + conn.getResponseCode());
            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            //2. Parser
            JSONParser jsonParser = new JSONParser();

            //3. To Object
            Object obj = jsonParser.parse(sb.toString());

            //4. To JsonObject
            JSONObject jsonObj = (JSONObject) obj;

            DustInfoDTO pDTO = new DustInfoDTO();
            pDTO.setPresnatnDt((String)jsonObj.get("presnatnDt"));

            pDTO.setFrcstOneDt((String)jsonObj.get("frcstOneDt"));
            pDTO.setFrcstOneCn((String)jsonObj.get("frcstOneCn"));

            pDTO.setFrcstTwoDt((String)jsonObj.get("frcstTwoDt"));
            pDTO.setFrcstTwoCn((String)jsonObj.get("frcstTwoCn"));

            pDTO.setFrcstThreeDt((String)jsonObj.get("frcstThreeDt"));
            pDTO.setFrcstThreeCn((String)jsonObj.get("frcstThreeCn"));

            pDTO.setFrcstFourDt((String)jsonObj.get("frcstFourDt"));
            pDTO.setFrcstFourCn((String)jsonObj.get("frcstFourCn"));

            rDTO = weatherMapper.insertDustInfo(pDTO);
        }

        String splitedOneCn = rDTO.getFrcstOneCn().split(region + " : ")[1].substring(0, 2);
        log.info(splitedOneCn);
        String splitedTwoCn = rDTO.getFrcstTwoCn().split(region + " : ")[1].substring(0, 2);
        log.info(splitedTwoCn);
        String splitedThreeCn = rDTO.getFrcstThreeCn().split(region + " : ")[1].substring(0, 2);
        log.info(splitedThreeCn);
        String splitedFourCn = rDTO.getFrcstFourCn().split(region + " : ")[1].substring(0, 2);
        log.info(splitedFourCn);

        rDTO.setFrcstOneCn(splitedOneCn);
        rDTO.setFrcstTwoCn(splitedTwoCn);
        rDTO.setFrcstThreeCn(splitedThreeCn);
        rDTO.setFrcstFourCn(splitedFourCn);


        return rDTO;
    }
}
