package com.resqmitra.module.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resqmitra.dto.ApiResponse;
import com.resqmitra.module.incident.entity.Incident;
import com.resqmitra.module.incident.exception.IncidentNotFoundException;
import com.resqmitra.module.incident.service.IncidentService;
import com.resqmitra.module.user.dto.RegisterUserModel;
import com.resqmitra.module.user.entity.User;
import com.resqmitra.module.user.exception.UserAlreadyCreatedException;
import com.resqmitra.module.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Autowired
	private IncidentService incService;
	
	@PostMapping("/register")
	public ResponseEntity<ApiResponse> register(@Valid @RequestBody RegisterUserModel model) throws UserAlreadyCreatedException{
		
		model.setPassword(passEncoder.encode(model.getPassword()));
		
		User user = userService.save(model);
		
		if(user==null) {
			log.warn("User is not created");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
			        .body(new ApiResponse(false, null, "User Not Created"));
		
		}
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse(true, null, "User is created"));
	}
	
	@GetMapping("/notify/nearby/{incidentId}")
	public ResponseEntity<ApiResponse> getNearbyVolunteer(@PathVariable Long incidentId) throws IncidentNotFoundException{
		Incident inc = incService.getIncidentById(incidentId);
		
		List<User> users = userService.getNearByVolunteer(inc);
		
		if(users == null || users.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(false, null, "No Nearby  Volunteer found"));
		
		return ResponseEntity.ok(new ApiResponse(true , users , "List of Near by Volunteer"));
			
	}
}
