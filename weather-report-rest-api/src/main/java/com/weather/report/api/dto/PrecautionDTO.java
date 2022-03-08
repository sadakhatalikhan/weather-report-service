package com.weather.report.api.dto;

import java.util.Date;

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
public class PrecautionDTO {
	private Date date;
	private double minTemp;
	private double maxTemp;
	private double precaution;
	private String windDescription;
	private String weatherDescription;
}
