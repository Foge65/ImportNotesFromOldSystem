package com.cleverdevsoftware.controller;

import com.cleverdevsoftware.dto.Client;
import com.cleverdevsoftware.dto.NoteRequest;
import com.cleverdevsoftware.dto.NoteResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ImportControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Value("${old.system.api.url}")
    private String url;

    @Test
    void getClients() {
        ResponseEntity<Client[]> response = restTemplate.postForEntity(
                url + "/clients",
                null,
                Client[].class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    void getNotesByClient() {
        NoteRequest noteRequest = new NoteRequest(
                "vhh4",
                "2019-09-18",
                "2021-09-17",
                "01588E84-D45A-EB98-F47F-716073A4F1EF"
        );

        ResponseEntity<NoteResponse[]> response = restTemplate.postForEntity(
                url + "/notes?=agency=vhh4",
                noteRequest,
                NoteResponse[].class
        );

        assertThat(response.getBody()).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

}
