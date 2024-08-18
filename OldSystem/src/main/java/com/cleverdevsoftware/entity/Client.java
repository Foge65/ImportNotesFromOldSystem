package com.cleverdevsoftware.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "client")
@Data
public class Client {

    @Column(name = "agency", nullable = false)
    private String agency;

    @Id
    @Column(name = "guid", nullable = false)
    private String guid;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "status", nullable = false)
    private short status;

    @Column(name = "dop", nullable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-dd-YYYY")
    private LocalDate dob;

    @Column(name = "created_date_time", nullable = false)
    private LocalDateTime createdDateTime;
}
