package com.cleverdevsoftware.newsystem.repository;

import com.cleverdevsoftware.newsystem.entity.PatientProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {

    @Query("SELECT patient FROM PatientProfile patient WHERE patient.oldClientGuid = :guid")
    PatientProfile findPatientProfileByOldClientGuid(@Param("guid") String guid);
}
