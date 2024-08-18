package com.cleverdevsoftware.controller;

import com.cleverdevsoftware.dto.Client;
import com.cleverdevsoftware.dto.NoteRequest;
import com.cleverdevsoftware.dto.NoteResponse;
import com.cleverdevsoftware.entity.CompanyUser;
import com.cleverdevsoftware.entity.PatientNote;
import com.cleverdevsoftware.entity.PatientProfile;
import com.cleverdevsoftware.repository.CompanyUserRepository;
import com.cleverdevsoftware.repository.PatientNoteRepository;
import com.cleverdevsoftware.repository.PatientProfileRepository;
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

    private final PatientProfileRepository patientProfileRepository;
    private final CompanyUserRepository companyUserRepository;
    private final PatientNoteRepository patientNoteRepository;

    @Value("${old.system.api.url}")
    private String oldSystemApiUrl;

    @Scheduled(cron = "0 15 1/2 * * ?")
    @Transactional
    public void importNotes() {
        try {
            List<Client> clients = getClients();
            importPatientProfiles(clients);
            for (Client client : clients) {
                List<NoteResponse> notes = getNotesByClient(client);
                for (NoteResponse note : notes) {
                    PatientProfile patientProfile = patientProfileRepository.findPatientProfileByOldClientGuid(client.getGuid());
                    if (patientProfile != null && isActivePatient(patientProfile)) {
                        CompanyUser companyUser = getOrCreateUser(note.getLoggedUser());
                        if (companyUser != null) {
                            importOrUpdateNote(note, patientProfile, companyUser);
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

    public void importPatientProfiles(List<Client> clients) {
        for (Client client : clients) {
            PatientProfile existingProfile = patientProfileRepository.findPatientProfileByOldClientGuid(client.getGuid());
            if (existingProfile == null) {
                PatientProfile newProfile = new PatientProfile();
                newProfile.setFirstName(client.getFirstName());
                newProfile.setLastName(client.getLastName());
                newProfile.setOldClientGuid(client.getGuid());
                newProfile.setStatusId(client.getStatus());
                patientProfileRepository.save(newProfile);
            }
        }
    }

    public List<NoteResponse> getNotesByClient(Client client) {
        ResponseEntity<NoteResponse[]> response = restTemplate.postForEntity(
                oldSystemApiUrl + "/notes?agency=" + client.getAgency(),
                new NoteRequest(
                        client.getAgency(),
                        "1999-01-01",
                        "2025-01-01",
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

    private CompanyUser getOrCreateUser(String login) {
        return companyUserRepository.findByLogin(login)
                .orElseGet(() -> {
                    CompanyUser newUser = new CompanyUser();
                    newUser.setLogin(login);
                    return companyUserRepository.save(newUser);
                });
    }

    private void importOrUpdateNote(NoteResponse noteResponse, PatientProfile patient, CompanyUser createdByUser) {
        PatientNote existingNote = patientNoteRepository.findByPatientAndCreatedDateTimeAndCreatedByUser(
                patient,
                noteResponse.getCreatedDateTime(),
                createdByUser
        );
        if (existingNote != null) {
            if (noteResponse.getModifiedDateTime().isAfter(existingNote.getLastModifiedDateTime())) {
                existingNote.setLastModifiedDateTime(noteResponse.getModifiedDateTime());
                existingNote.setLastModifiedByUser(createdByUser);
                existingNote.setNote(noteResponse.getComments());
                patientNoteRepository.save(existingNote);
            }
        } else {
            PatientNote newNote = new PatientNote();
            newNote.setCreatedDateTime(noteResponse.getCreatedDateTime());
            newNote.setLastModifiedDateTime(noteResponse.getModifiedDateTime());
            newNote.setCreatedByUser(createdByUser);
            newNote.setLastModifiedByUser(createdByUser);
            newNote.setNote(noteResponse.getComments());
            newNote.setPatient(patient);
            patientNoteRepository.save(newNote);
        }
    }

}
