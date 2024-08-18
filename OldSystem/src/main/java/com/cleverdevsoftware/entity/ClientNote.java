package com.cleverdevsoftware.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "client_note")
@Data
public class ClientNote {

    @Id
    @Column(name = "logged_user")
    private String loggedUser;
}
