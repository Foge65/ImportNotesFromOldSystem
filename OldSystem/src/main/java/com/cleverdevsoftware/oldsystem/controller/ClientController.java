package com.cleverdevsoftware.oldsystem.controller;

import com.cleverdevsoftware.oldsystem.entity.Client;
import com.cleverdevsoftware.oldsystem.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClientController {
    public static final Logger logger = LoggerFactory.getLogger(ClientController.class);

    private final ClientRepository clientRepository;

    @PostMapping("/clients")
    public ResponseEntity<List<Client>> getClients() {
        List<Client> clients = clientRepository.findAll();
        try {
            ResponseEntity.ok().body(clients);
        } catch (Exception e) {
            logger.warn("Error fetching clients: {}", e.getMessage());

            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(clients);
    }

}
