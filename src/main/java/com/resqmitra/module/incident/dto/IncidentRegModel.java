package com.resqmitra.module.incident.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IncidentRegModel {

	@NotNull(message = "Raised By User id can not be null")
	@NotBlank(message = "Raised By User id can not be blank")
	private String raisedBy;
	
	@NotNull(message = "Latitude cannot be null")
	private Double latitude;
	
	@NotNull(message = "Longitude cannot be null")
	private Double longitude;
}
