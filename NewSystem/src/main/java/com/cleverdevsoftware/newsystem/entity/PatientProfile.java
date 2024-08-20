package com.cleverdevsoftware.newsystem.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "patient_profile")
@Data
public class PatientProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "old_client_guid")
    private String oldClientGuid;

    @Column(name = "status_id", nullable = false)
    private short statusId;
}
