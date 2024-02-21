package com.redwoods.incidentservice.services;

import java.util.List;

import com.redwoods.incidentservice.dtos.IncidentResponseDto;
import com.redwoods.incidentservice.dtos.IncidentRequestDto;
import com.redwoods.incidentservice.models.Incident;

public interface IncidentService {

    IncidentResponseDto getIncident(Long incidentId);

    List<IncidentResponseDto> getIncidents();

    String deleteIncident(Long incidentId);

    IncidentResponseDto updateIncident(Long incidentId, IncidentRequestDto incidentRequestDto);

    Long addIncident(Incident incident);

}
