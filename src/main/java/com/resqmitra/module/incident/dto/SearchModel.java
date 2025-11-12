package com.resqmitra.module.incident.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchModel {

	private LocalDate startDate;
	
	private LocalDate endDate;
	
	private String keyword;
	
}
