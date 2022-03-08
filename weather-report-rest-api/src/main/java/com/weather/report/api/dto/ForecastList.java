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
public class ForecastList{
    public int dt;
    public MainDTO main;
    public ArrayList<WeatherDTO> weather;
    public CloudsDTO clouds;
    public WindDTO wind;
    public int visibility;
    public float pop;
    public SysDTO sys;
    public String dt_txt;
    
}