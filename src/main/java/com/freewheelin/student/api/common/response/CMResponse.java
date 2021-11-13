package com.freewheelin.student.api.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CMResponse {

    private String code = null;
    private String message = null;

    @Builder
    public CMResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
