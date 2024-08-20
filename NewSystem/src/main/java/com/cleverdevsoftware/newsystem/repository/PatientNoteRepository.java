package com.cleverdevsoftware.newsystem.repository;

import com.cleverdevsoftware.newsystem.entity.CompanyUser;
import com.cleverdevsoftware.newsystem.entity.PatientNote;
import com.cleverdevsoftware.newsystem.entity.PatientProfile;
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
