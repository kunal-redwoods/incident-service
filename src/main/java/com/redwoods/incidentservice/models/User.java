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
@Table(name = "users")
public class User extends BaseModel {
    private String userName;
    private String password;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private Incident incident;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ApprovalHistory> approvalHistories;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<ActionHistory> actionHistories;
}
