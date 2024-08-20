package com.cleverdevsoftware.oldsystem.service;

import com.cleverdevsoftware.oldsystem.dto.NoteResponse;
import com.cleverdevsoftware.oldsystem.entity.Note;
import com.cleverdevsoftware.oldsystem.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<NoteResponse> getNotesForClient(String agency, String dateFrom, String dateTo, String clientGuid) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate fromDate = LocalDate.parse(dateFrom, formatter);
        LocalDate toDate = LocalDate.parse(dateTo, formatter);

        LocalDateTime fromDateTime = fromDate.atStartOfDay();
        LocalDateTime toDateTime = toDate.atTime(LocalTime.MAX);

        List<Note> notes = noteRepository.findNoteByGuidAndDateFromEndingWith(clientGuid, fromDateTime, toDateTime);

        return notes.stream()
                .map(note -> {
                    NoteResponse response = new NoteResponse();
                    response.setComments(note.getComments());
                    response.setGuid(note.getGuid());
                    response.setModifiedDateTime(note.getModifiedDateTime());
                    response.setClientGuid(note.getClientGuid());
                    response.setDatetime(note.getDateTime());
                    response.setLoggedUser(note.getLoggedUser());
                    response.setCreatedDateTime(note.getCreatedDateTime());
                    return response;
                })
                .collect(Collectors.toList());
    }

}
