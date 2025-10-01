package com.resqmitra.module.user.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.resqmitra.module.incident.entity.Incident;
import com.resqmitra.module.user.dto.RegisterUserModel;
import com.resqmitra.module.user.entity.User;
import com.resqmitra.module.user.exception.UserAlreadyCreatedException;
import com.resqmitra.module.user.repo.UserRepository;
import com.resqmitra.utilities.Role;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService , UserService {
	
	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.getUserById(username);
		if(user!=null)
			return user;
		throw new UsernameNotFoundException("User with following email is not found : " + username);
	}
	
	@Override
	public User getUserById(String email) {
		Optional<User> userOp = userRepo.findById(email);
		if(userOp.isEmpty())
			return null;
		else
			return userOp.get();
	}

	@Override
	public User save(@Valid RegisterUserModel model) throws UserAlreadyCreatedException {
		
		User userTemp = this.getUserById(model.getEmail());
		
		if(userTemp!=null) {
			log.warn("User with given email id is already registered : {}" , model.getEmail());
			throw new UserAlreadyCreatedException("User is already created");
		}
		
		User user = User.builder()
				.email(model.getEmail())
				.name(model.getName())
				.password(model.getPassword())
				.phone(model.getPhoneNum())
				.role(model.getRole())
				.latitude(model.getLatitude())
				.longitude(model.getLongitude())
				.build();
		userRepo.save(user);
		return user;
	}

	@Override
	public User getUserByIdAndRole(String volunteerId, Role role) {
		Optional<User> userOp = userRepo.findByEmailAndRole(volunteerId , role);
		if(userOp.isEmpty())
			return null;
		else
			return userOp.get();
	}

	@Override
	public List<User> getNearByVolunteer(Incident incident) {
		List<User> users = userRepo.findNearbyVolunteers(incident.getLatitude(), incident.getLongitude(), 2);
		
		if(users==null || users.isEmpty())
			users = userRepo.findNearbyVolunteers(incident.getLatitude(), incident.getLongitude(), 5);
		
		return users;
	}

}
