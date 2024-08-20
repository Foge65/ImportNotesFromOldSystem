package com.cleverdevsoftware.oldsystem.repository;

import com.cleverdevsoftware.oldsystem.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, String> {

    @Query("SELECT n FROM Note n WHERE n.clientGuid = ?1 AND n.dateTime BETWEEN ?2 AND ?3")
    List<Note> findNoteByGuidAndDateFromEndingWith(String clientGuid, LocalDateTime dateFrom, LocalDateTime dateTo);
}
