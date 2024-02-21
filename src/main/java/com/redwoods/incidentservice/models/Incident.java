package com.redwoods.incidentservice.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incidents")
public class Incident extends BaseModel {

    private Long dateTime;
    private String severity;
    private String imageUrl;
    private String departmentId;
    private String description;
    private String location;
    private String title;
    private String userId;
    private String status;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incident")
    private List<User> users;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "incident_department_id")
    private Department department;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incident")
    private List<ApprovalHistory> approvalHistories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "incident")
    private List<ActionHistory> actionHistories;
}
