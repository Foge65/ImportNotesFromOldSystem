package com.cleverdevsoftware.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class Client {
    private String agency;
    private String guid;
    private String firstName;
    private String lastName;
    private short status;
    private LocalDate dob;
    private LocalDateTime createdDateTime;
}
