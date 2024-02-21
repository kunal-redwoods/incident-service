package com.redwoods.incidentservice.repository;


import com.redwoods.incidentservice.models.Incident;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IncidentRepository extends JpaRepository<Incident, Long> {
}
