package com.resqmitra.module.auth.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.resqmitra.module.auth.dto.LoginModel;
import com.resqmitra.module.auth.exception.UserIdAndPasswordNotMatchException;
import com.resqmitra.module.user.entity.User;
import com.resqmitra.module.user.service.UserService;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private PasswordEncoder passEncoder;
	
	@Autowired
	private UserService userService;

	@Override
	public User checkUser(@Valid LoginModel model) throws UserIdAndPasswordNotMatchException {
		
		User user = userService.getUserById(model.getEmailId());
		
		if(user == null) {
			log.warn("User not found for email: {}", model.getEmailId());
			throw new UsernameNotFoundException("User not found for email: " + model.getEmailId());
		}
		
		if(passEncoder.matches(model.getPassword(), user.getPassword()))
			return user;
		
		log.warn("User Id and Password does not match {} " + model.getEmailId());
		throw new UserIdAndPasswordNotMatchException("User id and Password does not match");
		
		
	}

}
