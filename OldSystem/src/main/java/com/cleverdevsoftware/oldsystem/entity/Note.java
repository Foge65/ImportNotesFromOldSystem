package com.cleverdevsoftware.oldsystem.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "note")
@Data
public class Note {

    @Column(name = "agency", nullable = false)
    private String agency;

    @Column(name = "date_from", nullable = false)
    private LocalDate dateFrom;

    @Column(name = "date_to", nullable = false)
    private LocalDate dateTo;

    @Column(name = "client_guid", nullable = false)
    private String clientGuid;

    @Column(name = "comments", nullable = false)
    private String comments;

    @Id
    @Column(name = "guid", nullable = false)
    private String guid;

    @Column(name = "modified_date_time", nullable = false)
    private LocalDateTime modifiedDateTime;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "logged_user", nullable = false)
    private String loggedUser;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;
}
