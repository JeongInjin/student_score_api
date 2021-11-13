package com.freewheelin.student.api.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CommonResponse<T> extends BaseResponse {
//    private StatusMessage status;
    private T data;
    private String error;

    public CommonResponse(T data){
        this.data = data;
        if(data instanceof List){

        }else{

        }
    }
}
