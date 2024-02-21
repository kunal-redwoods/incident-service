package com.redwoods.incidentservice.dtos;

import lombok.*;

@Getter
@Setter
public class IncidentRequestDto{

   private Long dateTime;
   private String severity;
   private String imageUrl;
   private String departmentId;
   private String description;
   private String location;
   private String title;
   private String userId;
   private String status;
}