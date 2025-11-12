package com.resqmitra.module.incident.specification;

import com.resqmitra.module.incident.entity.Incident;
import com.resqmitra.module.user.entity.User;

import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class IncidentSpecification {

    public static Specification<Incident> filter(
    		User user ,
    		User volunteer,
            LocalDateTime startDate,
            LocalDateTime endDate,
            String keyword,
            Incident.Status status
    ) {
        return Specification
        		.where(createdBy(user))
        		.and(volunteer(volunteer))
                .and(startDate(startDate))
                .and(endDate(endDate))
                .and(keyword(keyword))
                .and(status(status));
      }

    private static Specification<Incident> createdBy(User createdBy) {
        return (root, query, cb) -> {
            if (createdBy == null) return null;
            return cb.equal(root.get("createdBy"), createdBy);
        };
    }

    public static Specification<Incident> volunteer(User volunteer) {
        return (root, query, cb) -> {
            if (volunteer == null) return cb.conjunction();
            var join = root.join("volunteers");
            return cb.equal(join.get("volunteer"), volunteer);
        };
    }

    private static Specification<Incident> startDate(LocalDateTime startDate) {
        return (root, query, cb) -> {
            if (startDate == null) return null;
            return cb.greaterThanOrEqualTo(root.get("createdAt"), startDate);
        };
    }

    private static Specification<Incident> endDate(LocalDateTime endDate) {
        return (root, query, cb) -> {
            if (endDate == null) return null;
            return cb.lessThanOrEqualTo(root.get("createdAt"), endDate);
        };
    }

    private static Specification<Incident> keyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) return null;
            return cb.like(cb.lower(root.get("description")), "%" + keyword.toLowerCase() + "%");
        };
    }

    private static Specification<Incident> status(Incident.Status status) {
        return (root, query, cb) -> {
            if (status == null) return null;
            return cb.equal(root.get("status"), status);
        };
    }
}
