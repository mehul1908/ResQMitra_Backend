package com.resqmitra.module.incident.entity;

import java.time.LocalDateTime;

import com.resqmitra.module.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "incident_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IncidentMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne
    @JoinColumn(name = "incident_id")
    private Incident incident;

    @ManyToOne
    @JoinColumn(name = "sender_id")
    private User sender;

    private String message;

    @Builder.Default
    private LocalDateTime sentAt = LocalDateTime.now();
}
