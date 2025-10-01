package com.resqmitra.module.incident.repo;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.resqmitra.module.incident.entity.Incident;
import com.resqmitra.module.incident.entity.IncidentVolunteer;
import com.resqmitra.module.user.entity.User;

public interface IncidentRepo extends JpaRepository<Incident, Long> {


	List<Incident> findByRaisedBy(User user);

	List<Incident> findByCreatedAtBetween(LocalDate startDate, LocalDate endDate);
	
}
