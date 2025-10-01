package com.resqmitra.module.incident.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resqmitra.module.incident.entity.IncidentVolunteer;
import com.resqmitra.module.user.entity.User;

public interface IncidentVolunteerRepo extends JpaRepository<IncidentVolunteer, Long> {

	List<IncidentVolunteer> findByVolunteer(User user);

}
