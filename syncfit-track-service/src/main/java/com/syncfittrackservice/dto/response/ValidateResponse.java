package com.syncfittrackservice.dto.response;

import lombok.Getter;

@Getter
public class ValidateResponse {
    private boolean success;
    private int status;
    private boolean data;
    private String timestamp;
}