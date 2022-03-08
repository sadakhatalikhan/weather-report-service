package com.weather.report.api.dto;

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
public class CityDTO{
    public int id;
    public String name;
    public CoordDTO coord;
    public String country;
    public int population;
    public int timezone;
    public int sunrise;
    public int sunset;
}
