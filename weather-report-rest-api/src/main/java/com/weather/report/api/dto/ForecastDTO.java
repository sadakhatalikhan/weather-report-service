package com.weather.report.api.dto;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ForecastDTO {
	public String cod;
    public int message;
    public int cnt;
    public ArrayList<ForecastList> list;
    public CityDTO city;
	
}
