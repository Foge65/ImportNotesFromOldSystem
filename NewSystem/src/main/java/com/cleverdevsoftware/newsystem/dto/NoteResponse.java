package com.cleverdevsoftware.newsystem.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NoteResponse {
    private String comments;
    private String guid;
    private LocalDateTime modifiedDateTime;
    private String clientGuid;
    private LocalDateTime datetime;
    private String loggedUser;
    private LocalDateTime createdDateTime;
}
