package com.cleverdevsoftware.newsystem.controller;

import com.cleverdevsoftware.newsystem.dto.Client;
import com.cleverdevsoftware.newsystem.dto.NoteRequest;
import com.cleverdevsoftware.newsystem.dto.NoteResponse;
import com.cleverdevsoftware.newsystem.entity.CompanyUser;
import com.cleverdevsoftware.newsystem.entity.PatientNote;
import com.cleverdevsoftware.newsystem.entity.PatientProfile;
import com.cleverdevsoftware.newsystem.service.NoteService;
import com.cleverdevsoftware.newsystem.service.ProfileService;
import com.cleverdevsoftware.newsystem.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@RestController
@RequiredArgsConstructor
public class ImportController {
    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

    private final RestTemplate restTemplate;

    private final ProfileService profileService;
    private final UserService userService;
    private final NoteService noteService;

    @Value("${old.system.api.url}")
    private String oldSystemApiUrl;

    @Scheduled(cron = "0 15 1/2 * * ?")
    @Transactional
    public void importNotes() {
        try {
            List<Client> clients = getClients();
            for (Client client : clients) {
                List<NoteResponse> notes = getNotesByClient(client, "1999-01-01", "2025-01-01");
                for (NoteResponse noteResponse : notes) {
                    PatientProfile patientProfile = profileService.importPatientProfile(client);
                    if (patientProfile == null) {
                        profileService.createPatientProfile(client);
                    } else {
                        profileService.importPatientProfile(client);
                        if (isActivePatient(patientProfile)) {
                            CompanyUser companyUser = userService.getOrCreateUser(noteResponse.getLoggedUser());
                            PatientNote patientNote = noteService.getPatientNote(patientProfile, noteResponse, companyUser);
                            if (patientNote == null) {
                                noteService.createNote(noteResponse, patientProfile, companyUser);
                            } else {
                                noteService.importNote(noteResponse, patientNote, companyUser);
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Error during import process: ", e);
        }
    }

    public List<Client> getClients() {
        ResponseEntity<Client[]> response = restTemplate.postForEntity(
                oldSystemApiUrl + "/clients",
                null,
                Client[].class
        );
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }

    public List<NoteResponse> getNotesByClient(Client client, String dateFrom, String dateTo) {
        ResponseEntity<NoteResponse[]> response = restTemplate.postForEntity(
                oldSystemApiUrl + "/notes?agency=" + client.getAgency(),
                new NoteRequest(
                        client.getAgency(),
                        dateFrom,
                        dateTo,
                        client.getGuid()
                ),
                NoteResponse[].class
        );
        if (response.getStatusCode() == HttpStatus.OK) {
            return Arrays.asList(Objects.requireNonNull(response.getBody()));
        } else {
            logger.error("Failed to retrieve notes for client GUID: " + client.getGuid() + ". Status code: " + response.getStatusCode());
            return Collections.emptyList();
        }
    }

    private boolean isActivePatient(PatientProfile patient) {
        return patient.getStatusId() == 200 || patient.getStatusId() == 210 || patient.getStatusId() == 230;
    }

}
