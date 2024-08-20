package com.cleverdevsoftware.newsystem.service;

import com.cleverdevsoftware.newsystem.dto.Client;
import com.cleverdevsoftware.newsystem.entity.PatientProfile;
import com.cleverdevsoftware.newsystem.repository.PatientProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final PatientProfileRepository patientProfileRepository;

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

}
