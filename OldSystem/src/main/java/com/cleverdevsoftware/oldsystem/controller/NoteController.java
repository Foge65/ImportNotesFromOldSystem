package com.cleverdevsoftware.oldsystem.controller;

import com.cleverdevsoftware.oldsystem.dto.NoteRequest;
import com.cleverdevsoftware.oldsystem.dto.NoteResponse;
import com.cleverdevsoftware.oldsystem.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NoteController {
    public static final Logger logger = LoggerFactory.getLogger(NoteController.class);

    private final NoteService noteService;

    @PostMapping("/notes")
    public ResponseEntity<List<NoteResponse>> getNotes(@RequestParam String agency, @RequestBody NoteRequest noteRequest) {
        if (agency.equals(noteRequest.getAgency())) {
            return ResponseEntity
                    .ok()
                    .body(noteService.getNotesForClient(
                            noteRequest.getAgency(),
                            noteRequest.getDateFrom(),
                            noteRequest.getDateTo(),
                            noteRequest.getClientGuid()
                    ));
        } else {
            logger.warn("Invalid agency provided: {}", agency);

            return ResponseEntity.badRequest().build();
        }

    }

}
