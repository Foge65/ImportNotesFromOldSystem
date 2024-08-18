package com.cleverdevsoftware.controller;

import com.cleverdevsoftware.dto.NoteRequest;
import com.cleverdevsoftware.dto.NoteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NoteControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void getNotes_return_note_response_for_agency_vhh4() {
        NoteRequest noteRequest = new NoteRequest(
                "vhh4",
                "2019-09-18",
                "2021-09-17",
                "01588E84-D45A-EB98-F47F-716073A4F1EF"
        );

        ResponseEntity<NoteResponse[]> response = restTemplate.postForEntity(
                "/api/notes?=agency=vhh4",
                noteRequest,
                NoteResponse[].class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getStatusCode())).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getNotes_return_note_response_for_agency_vhh5() {
        NoteRequest noteRequest = new NoteRequest(
                "vhh5",
                "2019-09-18",
                "2021-09-17",
                "01588E84-D45A-EB98-F47F-716073A4F13C"
        );

        ResponseEntity<NoteResponse[]> response = restTemplate.postForEntity(
                "/api/notes?=agency=vhh5",
                noteRequest,
                NoteResponse[].class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(Objects.requireNonNull(response.getStatusCode())).isEqualTo(HttpStatus.OK);
    }

}
