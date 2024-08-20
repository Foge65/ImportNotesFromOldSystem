package com.cleverdevsoftware.newsystem.service;

import com.cleverdevsoftware.newsystem.dto.Client;
import com.cleverdevsoftware.newsystem.entity.PatientProfile;
import com.cleverdevsoftware.newsystem.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final PatientProfileRepository patientProfileRepository;

    public PatientProfile importPatientProfile(Client clientGuid) {
        return patientProfileRepository.findPatientProfileByOldClientGuid(clientGuid.getGuid());
    }

    public void createPatientProfile(Client client) {
        PatientProfile newProfile = new PatientProfile();
        newProfile.setFirstName(client.getFirstName());
        newProfile.setLastName(client.getLastName());
        newProfile.setOldClientGuid(client.getGuid());
        newProfile.setStatusId(client.getStatus());
        patientProfileRepository.save(newProfile);
    }

}
