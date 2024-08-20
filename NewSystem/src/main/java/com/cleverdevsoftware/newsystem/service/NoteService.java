package com.cleverdevsoftware.newsystem.service;

import com.cleverdevsoftware.newsystem.dto.NoteResponse;
import com.cleverdevsoftware.newsystem.entity.CompanyUser;
import com.cleverdevsoftware.newsystem.entity.PatientNote;
import com.cleverdevsoftware.newsystem.entity.PatientProfile;
import com.cleverdevsoftware.newsystem.repository.PatientNoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final PatientNoteRepository patientNoteRepository;

    public PatientNote getPatientNote(PatientProfile patientProfile, NoteResponse noteResponse, CompanyUser companyUser) {
        return patientNoteRepository.findByPatientAndCreatedDateTimeAndCreatedByUser(
                patientProfile,
                noteResponse.getCreatedDateTime(),
                companyUser
        );
    }

    public void importNote(NoteResponse noteResponse, PatientNote patientNote, CompanyUser companyUser) {
        if (noteResponse.getModifiedDateTime().isAfter(patientNote.getLastModifiedDateTime())) {
            patientNote.setLastModifiedDateTime(noteResponse.getModifiedDateTime());
            patientNote.setLastModifiedByUser(companyUser);
            patientNote.setNote(noteResponse.getComments());
            patientNoteRepository.save(patientNote);
        }
    }

    public void createNote(NoteResponse noteResponse, PatientProfile patientProfile, CompanyUser companyUser) {
        PatientNote patientNote = new PatientNote();
        patientNote.setCreatedDateTime(noteResponse.getCreatedDateTime());
        patientNote.setLastModifiedDateTime(noteResponse.getModifiedDateTime());
        patientNote.setCreatedByUser(companyUser);
        patientNote.setLastModifiedByUser(companyUser);
        patientNote.setNote(noteResponse.getComments());
        patientNote.setPatient(patientProfile);
        patientNoteRepository.save(patientNote);
    }

}
