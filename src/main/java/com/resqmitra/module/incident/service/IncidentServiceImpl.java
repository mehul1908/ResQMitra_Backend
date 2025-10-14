package com.resqmitra.module.incident.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.resqmitra.module.auth.exception.UnauthorizedUserException;
import com.resqmitra.module.incident.dto.DateModel;
import com.resqmitra.module.incident.dto.IncidentRegModel;
import com.resqmitra.module.incident.dto.IncidentVolunteerRegModel;
import com.resqmitra.module.incident.entity.Incident;
import com.resqmitra.module.incident.entity.IncidentVolunteer;
import com.resqmitra.module.incident.exception.IncidentNotFoundException;
import com.resqmitra.module.incident.repo.IncidentRepo;
import com.resqmitra.module.incident.repo.IncidentVolunteerRepo;
import com.resqmitra.module.notify.service.EmailService;
import com.resqmitra.module.user.entity.User;
import com.resqmitra.module.user.service.UserService;
import com.resqmitra.utilities.Role;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IncidentServiceImpl implements IncidentService{
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private IncidentRepo incRepo;
	
	@Autowired
	private IncidentVolunteerRepo incVolunteerRepo;

	@Autowired
	private EmailService emailService;
	
	@Override
	@PreAuthorize("hasRole('CITIZEN')")
	public Incident registerIncident(@Valid IncidentRegModel model) throws MessagingException {
		
		Incident inc = Incident.builder()
				.latitude(model.getLatitude())
				.longitude(model.getLongitude())
				.build();
		
		incRepo.save(inc);
		
		
		List<User> users = userService.getNearByVolunteer(inc);
		emailService.sendIncidentEmailToVolunteers(users, inc);
		// Notify them
		return inc;
	}
	
	@Override
	public Incident getIncidentById(Long incidentId) throws IncidentNotFoundException {
		Optional<Incident> incOp = incRepo.findById(incidentId);
		
		if(incOp.isEmpty())
			return null;
		return incOp.get();
	}

	@Override
	public IncidentVolunteer registerIncVolunteer(@Valid IncidentVolunteerRegModel model) throws IncidentNotFoundException {
		
		Incident inc = this.getIncidentById(model.getIncidentId());
		
		if(inc==null) {
			log.warn("Incident with given id not found: {}" , model.getIncidentId());
			throw new IncidentNotFoundException(model.getIncidentId());
		}
		
		User user = userService.getUserByIdAndRole(model.getVolunteerId() , Role.ROLE_VOLUNTEER);
		
		if(user==null){
			log.warn("User with given id not found: {}" , model.getVolunteerId());
			throw new UsernameNotFoundException("User with id " + model.getVolunteerId() + " not found");
		}
		
		IncidentVolunteer incVolunteer = IncidentVolunteer.builder()
				.incident(inc)
				.volunteer(user)
				.build();
		
		incVolunteerRepo.save(incVolunteer);
		inc.setStatus(Incident.Status.IN_PROGRESS);
		return incVolunteer;
		
	}

	@Override
	public List<Incident> getAllIncident() {
		List<Incident> incidents = incRepo.findAll();
		return incidents;
	}

	@Override
	public List<Incident> getIncidentByVolunteer() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof User user) {
				
			List<IncidentVolunteer> volunteers =  incVolunteerRepo.findByVolunteer(user);
			return volunteers.stream()
                    .map(IncidentVolunteer::getIncident)
                    .collect(Collectors.toList());

		} else {
			throw new UnauthorizedUserException("User is unauthentical or not valid");
		}		
	}

	@Override
	public List<Incident> getIncidentByDate(DateModel model) {
		List<Incident> incidents =  incRepo.findByCreatedAtBetween(model.getStartDate().atStartOfDay() , model.getEndDate().atTime(LocalTime.MAX));
		return incidents;
	}

	@Override
	public void resolveIncident(Incident inc) {
		
		if(inc == null) {
			throw new IncidentNotFoundException();
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getPrincipal() instanceof User user) {
			
			if(isVolunteerRelated(user , inc)) {
				inc.setStatus(Incident.Status.RESOLVED);
				inc.setResolvedAt(LocalDateTime.now());
				incRepo.save(inc);
				return;
			}

		}
		throw new UnauthorizedUserException("User is unauthentical or not valid");
		
	}

	private boolean isVolunteerRelated(User user, Incident inc) {
		if(!user.getRole().equals(Role.ROLE_VOLUNTEER))
			return false;
		Optional<IncidentVolunteer> vol = incVolunteerRepo.findByVolunteerAndIncident(user , inc);
		return vol.isPresent();
	}

	

}
