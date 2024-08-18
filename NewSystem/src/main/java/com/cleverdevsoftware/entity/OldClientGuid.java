package com.cleverdevsoftware.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "old_client_guid")
@Data
public class OldClientGuid {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guid", nullable = false)
    private String guid;

    @ManyToOne()
    @JoinColumn(name = "patient_profile_id", nullable = false)
    private PatientProfile patientProfile;
}
