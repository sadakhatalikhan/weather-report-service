package com.weather.report.api.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.weather.report.api.response.WeatherResponse;

public interface WeatherReportService {
	ResponseEntity<List<WeatherResponse>> getCityWeatherReport(String city);
}
