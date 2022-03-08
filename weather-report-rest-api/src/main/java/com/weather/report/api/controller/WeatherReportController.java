package com.weather.report.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.weather.report.api.response.WeatherResponse;
import com.weather.report.api.service.WeatherReportService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class WeatherReportController {
	
	private final WeatherReportService weatherReportService;
	
	// GET
	@GetMapping("/getCity-weather-report/{city}")
	public ResponseEntity<List<WeatherResponse>> getCityWeatherReport(@PathVariable String city) {
		return weatherReportService.getCityWeatherReport(city);
	}
	
}
