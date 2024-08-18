package com.cleverdevsoftware.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "company_user")
@Data
public class CompanyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String login;
}
