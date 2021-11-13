package com.freewheelin.student.api.common.response;

import com.freewheelin.student.api.dto.ErrResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class ResPonseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request){
        List<String> errors = new ArrayList<String>();
        String field = null;
        String rejectValue = null;
        String message = null;

        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
            field = error.getField();
            rejectValue = error.getRejectedValue().toString();
            message = error.getDefaultMessage();
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        CMResponse cmResponse = CMResponse.builder()
                .code(Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue())
                .message(message)
                .build();
        BaseResponse response = BaseResponse.builder()
                .data(null)
                .error(cmResponse)
                .build();

        return new ResponseEntity<>(response, headers, HttpStatus.BAD_REQUEST);

//        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                .body(new CMResponse(Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue(), message));
    }

    public ResponseEntity<Object> errResponseException(ErrResponseDto errResponseDto) {
        String statusMessage = errResponseDto.getStatusMessage();
        String resMsg = statusMessage.replaceAll(errResponseDto.getRegexKey(), errResponseDto.getReplaceStr());

        HttpHeaders headers= new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        CMResponse cmResponse = CMResponse.builder()
                .code(errResponseDto.getStatusCode())
                .message(resMsg)
                .build();
        BaseResponse response = BaseResponse.builder()
                .data(null)
                .error(cmResponse)
                .build();
        return new ResponseEntity<>(response, headers, errResponseDto.getHttpStatus());

//        return ResponseEntity.status(errResponseDto.getHttpStatus())
//                .body(new CMResponse(errResponseDto.getStatusCode(), resMsg));
    }
}
