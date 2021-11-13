package com.freewheelin.student.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@Builder
public class ErrResponseDto {
    private HttpStatus httpStatus;

    private String statusCode;

    private String statusMessage;

    private String regexKey;

    private String replaceStr;


    public ErrResponseDto(HttpStatus httpStatus, String statusCode, String statusMessage, String regexKey, String replaceStr) {
        this.httpStatus = httpStatus;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.regexKey = regexKey;
        this.replaceStr = replaceStr;
    }
}
