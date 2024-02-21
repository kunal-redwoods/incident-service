package com.redwoods.incidentservice.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.redwoods.incidentservice.models.Incident;
import com.redwoods.incidentservice.repository.IncidentRepository;
import com.redwoods.incidentservice.services.IncidentService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.redwoods.incidentservice.dtos.IncidentResponseDto;
import com.redwoods.incidentservice.dtos.IncidentRequestDto;
import com.redwoods.incidentservice.exceptions.NotFoundException;

@Service
public class IncidentServiceImpl implements IncidentService {

   private IncidentRepository incidentRepository;

   private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(Incident.class);

    public IncidentServiceImpl(IncidentRepository incidentRepository, ModelMapper modelMapper) {
        this.incidentRepository = incidentRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public IncidentResponseDto getIncident(Long incidentId) throws NotFoundException {
        try {
            Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);
            if (optionalIncident.isEmpty()) {
                throw new NotFoundException("Incident Doesn't exist.");
            }

            return modelMapper.map(optionalIncident.get(), IncidentResponseDto.class);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing getIncident", ex);
            throw new RuntimeException("Error occurred while processing getIncident", ex);
        }
    }

    @Override
    public List<IncidentResponseDto> getIncidents() {
        try {
            List<Incident> incidents = incidentRepository.findAll();
            List<IncidentResponseDto> IncidentResponseDtos = null;
            if (!incidents.isEmpty()) {
                IncidentResponseDtos = new ArrayList<>();
                for (Incident incident : incidents) {
                    IncidentResponseDtos.add(modelMapper.map(incident, IncidentResponseDto.class));
                }
            }
            return IncidentResponseDtos;
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing getIncidents", ex);
            throw new RuntimeException("Error occurred while processing getIncidents", ex);
        }
    }

    @Override
    public String deleteIncident(Long incidentId) {
        if (incidentRepository.findById(incidentId).isPresent()) {
            incidentRepository.deleteById(incidentId);
            return "Incident Deleted Successfully!!";
        }
        return "No Incident exists in the database with the provided id.";
    }

    @Override
    public IncidentResponseDto updateIncident(Long incidentId, IncidentRequestDto IncidentRequestDto) {
        try {
            Optional<Incident> optionalIncident = incidentRepository.findById(incidentId);
            if (optionalIncident.isPresent()) {
                Incident incident = optionalIncident.get();

                incident.setLast_updated_by("admin");
                incident.setLast_updated_on(System.currentTimeMillis());

                return modelMapper.map(incidentRepository.save(incident), IncidentResponseDto.class);
            } else {
                return null;
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing updateIncidents", ex);
            throw new RuntimeException("Error occurred while processing updateIncidents", ex);
        }
    }

    @Override
    public Long addIncident(Incident incident) {
        try {
            Incident newIncident = incidentRepository.save(incident);
            return newIncident.getId();
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing addIncidents", ex);
            throw new RuntimeException("Error occurred while processing addIncidents", ex);
        }
    }
}
