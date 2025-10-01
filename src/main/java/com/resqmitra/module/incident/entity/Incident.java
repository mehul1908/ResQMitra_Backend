package com.resqmitra.module.incident.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.resqmitra.module.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "incident")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long incidentId;

    @ManyToOne
    @JoinColumn(name = "citizen_id")
    private User raisedBy;
    
    @Builder.Default
    @OneToMany(mappedBy = "incident", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IncidentVolunteer> volunteers = new ArrayList();


    @Enumerated(EnumType.STRING)
    private Type type;

    private String description;
    private Double latitude;
    private Double longitude;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Status status = Status.ACTIVE;

    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime resolvedAt;

    public enum Type {
        MEDICAL, FIRE, ACCIDENT, NATURAL_DISASTER, OTHER
    }

    public enum Status {
        ACTIVE, IN_PROGRESS, RESOLVED, ESCALATED
    }
}

