package com.cleverdevsoftware.newsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoteRequest {
    private String agency;
    private String dateFrom;
    private String dateTo;
    private String clientGuid;
}
