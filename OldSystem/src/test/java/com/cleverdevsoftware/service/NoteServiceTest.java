package com.cleverdevsoftware.service;

import com.cleverdevsoftware.dto.NoteResponse;
import com.cleverdevsoftware.entity.Note;
import com.cleverdevsoftware.repository.NoteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NoteServiceTest {

    @Mock
    private NoteRepository noteRepository;

    @InjectMocks
    private NoteService noteService;

    @Test
    void getNotesForClient_return_note_response() {
        String clientGuid = "01588E84-D45A-EB98-F47F-716073A4F1EF";

        Note note1 = new Note();
        note1.setAgency("vhh4");
        note1.setDateFrom(LocalDate.now());
        note1.setDateTo(LocalDate.now());
        note1.setClientGuid(clientGuid);
        note1.setComments("Patient Care Coordinator, reached out to patient caregiver is still in the hospital");
        note1.setGuid("20CBCEDA-3764-7F20-0BB6-4D6DD46BA9F8");
        note1.setModifiedDateTime(LocalDateTime.now());
        note1.setDateTime(LocalDateTime.now());
        note1.setLoggedUser("p.vasya");
        note1.setCreatedDateTime(LocalDateTime.now());

        when(noteRepository.findNoteByGuidAndDateFromEndingWith(
                eq(clientGuid),
                any(LocalDateTime.class),
                any(LocalDateTime.class)
        ))
                .thenReturn(List.of(note1));

        List<NoteResponse> responses = noteService.getNotesForClient(
                "vhh4",
                "2019-09-18",
                "2021-09-17",
                clientGuid
        );

        assertThat(responses).hasSize(1);
    }

}
