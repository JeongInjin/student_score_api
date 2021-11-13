package com.freewheelin.student.api.common.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseResponse {
    private Object data;
    private Object error;

    @Builder
    public BaseResponse(Object data, Object error) {
        this.data = data;
        this.error = error;
    }
}
