package com.weather.report.api.service.impl;


import static com.weather.report.api.constants.WeatherReportConstants.DECIMAL_PLACES;
import static com.weather.report.api.constants.WeatherReportConstants.KELVIN_TO_CELSIUS;
import static com.weather.report.api.constants.WeatherReportConstants.MPS_TO_MPH;
import static com.weather.report.api.constants.WeatherReportConstants.THUNDERSTORMS;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.weather.report.api.dto.ForecastDTO;
import com.weather.report.api.response.WeatherResponse;
import com.weather.report.api.service.WeatherReportService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WeatherReportServiceImpl implements WeatherReportService {

	@Value("${forecast.api.url}")
    private String apiURL;
	
	@Value("${forecast.api.key}")
    private String apiKey;
	
	@Value("${forecast.api.cnt}")
	private String apiCnt;
	
	private final RestTemplate restTemplate;
	private final MessageSource messageSource;
	
	private static final DecimalFormat dfZero = new DecimalFormat(DECIMAL_PLACES);
	private final Logger logger = LoggerFactory.getLogger("mainlogger");
	
	@Override
	public ResponseEntity<List<WeatherResponse>> getCityWeatherReport(String city) {
		logger.info("WeatherReportServiceImpl.java - weather report implementation execution started {} ", city);
		final String URL = apiURL+"?q="+city+"&appid="+apiKey+"&cnt="+apiCnt;
		logger.info("WeatherReportServiceImpl.java - URL {} ", URL);
		String result = null;
		ForecastDTO responseResult = new ForecastDTO();
		
		List<WeatherResponse> responseList = new ArrayList<>();
		try {
			result = restTemplate.getForObject(URL, String.class);
			Gson gson = new Gson();
			responseResult = gson.fromJson(result, ForecastDTO.class);
			if (null != responseResult) {
				// add all required filed into hashmap
				HashMap<List<String>, String> hm = new HashMap<>();
				for(int i=0; i<responseResult.getList().size(); i++) {
					
					List<String> list = new ArrayList<>();
					list.add(String.valueOf(responseResult.getList().get(i).getMain().getTemp_min()));
					list.add(String.valueOf(responseResult.getList().get(i).getMain().getTemp_max()));
					list.add(responseResult.getList().get(i).getWeather().get(0).getDescription());
					list.add(String.valueOf(responseResult.getList().get(i).getWind().getSpeed()));
					
					hm.put(list, responseResult.getList().get(i).getDt_txt().substring(0,10));
				}
				logger.info("WeatherReportServiceImpl.java - mapped all required data into map {} ",hm);
				
				HashMap<String, List<String>> hashMap = new HashMap<>();
				
				// Get unique values from the hashmap
				List<String> datesList = new ArrayList<>();
				for (Entry<List<String>, String> entry : hm.entrySet()) {
					datesList.add(entry.getValue());
		        }
				List<String> datesList1 = datesList.stream().distinct().collect(Collectors.toList());
				logger.info("WeatherReportServiceImpl.java - Unique Dates {} ", datesList1);
				
				// get keys from the hm and arrange all the values in new hashmap on datewise
				
				for(String str : datesList1) {
					List<List<String>> li = getKeyFromValue(hm, str);
					
					// high and low temperatures
					List<String> minTempList = new ArrayList<>();
					List<String> maxTempList = new ArrayList<>();
					List<String> windSpeedList = new ArrayList<>();
					List<String> weatherDescList = new ArrayList<>();
					
					for(List<String> list : li) {
						minTempList.add(list.get(0));
						maxTempList.add(list.get(1));
						weatherDescList.add(list.get(2));
						windSpeedList.add(list.get(3));
					}
					
					double tempMin = minTempList.stream().mapToDouble(v -> Double.valueOf(v)).min().getAsDouble()-KELVIN_TO_CELSIUS;
					double tempMax = minTempList.stream().mapToDouble(v -> Double.valueOf(v)).max().getAsDouble()-KELVIN_TO_CELSIUS;
					double windSpeed = windSpeedList.stream().mapToDouble(v -> Double.valueOf(v)).max().getAsDouble()*MPS_TO_MPH;
					String tempPrecaution;
					String highWindDescription = "";
					String thunderstorms = "";
					if(tempMin > 40 || tempMax > 40) { // -273.15;
						tempPrecaution = messageSource.getMessage("user_sunscreen_lotion", null, Locale.US);
								
					} else {
						tempPrecaution = messageSource.getMessage("carry_umbrella", null, Locale.US);
					}
					
					if(windSpeed > 10) {
						highWindDescription = messageSource.getMessage("hign_wind_speed", null, Locale.US);
					}
					
					if(weatherDescList.contains(THUNDERSTORMS)) {
						thunderstorms = messageSource.getMessage("thunderstorms", null, Locale.US);
					}
				
					responseList.add(WeatherResponse.builder()
								.date(str)
								.minTemp(dfZero.format(tempMin))
								.maxTemp(dfZero.format(tempMax))
								.tempPrecaution(tempPrecaution)
								.windSpeed(dfZero.format(windSpeed))
								.highWindDescription(highWindDescription)
								.thunderstorms(thunderstorms)
								.build());
					
				}
			}
			logger.info("WeatherReportServiceImpl.java - weather report implementation execution ended {} ", city);
		} catch (Exception e) {
			logger.info("WeatherReportServiceImpl.java - Exception {} ", e);
		}
		return ResponseEntity.ok(responseList);
		
	}
	
	
	private static List<List<String>> getKeyFromValue(HashMap<List<String>, String> hm, String v) {
		return hm.entrySet().stream().filter(entry -> Objects.equals(entry.getValue(), v))
				.map(Map.Entry::getKey)
				.collect(Collectors.toList());
	}
}
