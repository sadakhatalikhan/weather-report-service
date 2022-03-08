package com.weather.report.api.response;

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
public class WeatherResponse{
   private String date;
   private String minTemp;
   private String maxTemp;
   private String tempPrecaution;
   private String windSpeed;
   private String highWindDescription;
   private String thunderstorms;
   
}
