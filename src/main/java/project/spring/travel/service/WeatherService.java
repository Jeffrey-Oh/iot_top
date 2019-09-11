package project.spring.travel.service;

import java.util.List;

import project.spring.travel.model.Weather;

/**
 * @fileName    : WeatherService.java
 * @author      : 김민석
 * @description : 날씨 CRUD Service 계층을 위한 인터페이스
 * @lastUpdate  : 2019. 5. 10.
 */
public interface WeatherService {
    /**
     * 날씨 정보 저장 기능
     * @param Weather - 저장할 날씨 정보가 담긴 Beans
     * @throws Exception
     */
    public void insertWeather(Weather weather) throws Exception;
    
    /**
     * 특정 날씨를 조회하기 위한 기능
     * @param Weather
     * @return Weather - 조회된 날씨
     * @throws Exception
     */
    public Weather selectWeather(Weather weather) throws Exception;
    
    /**
     * 날씨 목록 조회 기능
     * @return List<Weather> - 조회된 날씨 목록이 담긴 List
     * @throws Exception
     */
    public List<Weather> selectWeatherList(Weather weather) throws Exception;
    
    /**
     * 날씨 정보 삭제 기능
     * @throws Exception
     */
    public void deleteWeather() throws Exception;
    
    
    
    public Weather selectWeatherListMaxMin(Weather weather) throws Exception;
}
