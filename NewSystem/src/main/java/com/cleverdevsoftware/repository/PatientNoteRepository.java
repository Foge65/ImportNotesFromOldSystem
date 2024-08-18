package com.cleverdevsoftware.repository;

import com.cleverdevsoftware.entity.CompanyUser;
import com.cleverdevsoftware.entity.PatientNote;
import com.cleverdevsoftware.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PatientNoteRepository extends JpaRepository<PatientNote, Long> {
    PatientNote findByPatientAndCreatedDateTimeAndCreatedByUser(
            PatientProfile patient,
            LocalDateTime createdDateTime,
            CompanyUser createdByUse
    );

}
