package com.redwoods.incidentservice.models;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/* audit columns */
	private Long created_on = System.currentTimeMillis();
	private Long last_updated_on = System.currentTimeMillis();
	private String created_by = "admin";
	private String last_updated_by = "admin";
	private boolean is_deleted;
}
