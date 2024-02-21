package com.redwoods.incidentservice.controllers;

import java.util.List;

import com.redwoods.incidentservice.models.Incident;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.redwoods.incidentservice.dtos.IncidentResponseDto;
import com.redwoods.incidentservice.dtos.IncidentRequestDto;
import com.redwoods.incidentservice.services.IncidentService;

@CrossOrigin
@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private IncidentService incidentService;
    private ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(IncidentController.class);

    public IncidentController(IncidentService incidentService, ModelMapper modelMapper) {
        this.incidentService = incidentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{incidentId}")
    public ResponseEntity<?> getIncident(@PathVariable("incidentId") Long incidentId) {
        try {
            IncidentResponseDto incidentResponseDto = incidentService.getIncident(incidentId);
            return new ResponseEntity<>(incidentResponseDto, HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /incidents/" + incidentId, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllIncidents() {
        try{
            List<IncidentResponseDto> incidentResponseDto = incidentService.getIncidents();
            if (incidentResponseDto != null) {
                return new ResponseEntity<>(incidentResponseDto, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No Incidents available!!", HttpStatus.OK);
            }
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /incidents", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> addIncident(@RequestBody IncidentRequestDto incidentRequestDto) {
        try{
            Incident incident = modelMapper.map(incidentRequestDto, Incident.class);
            Long incidentId = incidentService.addIncident(incident);
            return new ResponseEntity<>(String.format("New Incident added %s successfully.", incidentId), HttpStatus.CREATED);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /incidents", ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{incidentId}")
    public ResponseEntity<?> updateIncident(@PathVariable("incidentId") Long incidentId, @RequestBody IncidentRequestDto incidentRequestDto) {
        try{
            IncidentResponseDto incidentResponseDto = incidentService.updateIncident(incidentId, incidentRequestDto);
            if (incidentResponseDto != null)
                return new ResponseEntity<>(incidentResponseDto, HttpStatus.CREATED);
            else
                return new ResponseEntity<>(String.format("No Incident exist in the database with provided id %s.", incidentId), HttpStatus.OK);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while processing /incidents/" + incidentId, ex);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{incidentId}")
    public ResponseEntity<String> deleteIncident(@PathVariable("incidentId") Long incidentId) {
        String message = incidentService.deleteIncident(incidentId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
