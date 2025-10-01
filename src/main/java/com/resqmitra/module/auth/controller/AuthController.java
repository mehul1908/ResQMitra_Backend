package com.resqmitra.module.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resqmitra.config.JWTUtils;
import com.resqmitra.dto.ApiResponse;
import com.resqmitra.dto.LoginResponse;
import com.resqmitra.module.auth.dto.LoginModel;
import com.resqmitra.module.auth.exception.UserIdAndPasswordNotMatchException;
import com.resqmitra.module.auth.service.AuthService;
import com.resqmitra.module.user.entity.User;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/auth")
@Slf4j
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	@PostMapping("/login")
	public ResponseEntity<ApiResponse> login(@RequestBody @Valid LoginModel model) throws UserIdAndPasswordNotMatchException{
		
		User user = authService.checkUser(model);
		
		if(user!=null) {
			String token = jwtUtils.generateToken(model.getEmailId());
			LoginResponse lr = new LoginResponse(user.getName(), user.getEmail(), user.getRole().toString() , token);
			log.info("User Logged In :{} " ,  user.getEmail());
			return ResponseEntity.ok(new ApiResponse(true, lr, "User login successfully"));
		}
		else {
			log.warn("User not found for email: {}", model.getEmailId());
			throw new UsernameNotFoundException("User not found for email: " + model.getEmailId());
		}
	}
	
	
}
