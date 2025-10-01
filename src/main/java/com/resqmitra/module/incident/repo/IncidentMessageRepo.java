package com.resqmitra.module.incident.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import com.resqmitra.module.incident.entity.IncidentMessage;

public interface IncidentMessageRepo extends JpaRepository<IncidentMessage, Long> {

}
