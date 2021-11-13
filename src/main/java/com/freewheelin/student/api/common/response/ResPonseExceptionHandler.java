package com.freewheelin.student.api.common.response;

import com.freewheelin.student.api.util.StringUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ResPonseExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<? extends BaseResponse> handleMethodArgumentException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new CMResponse(Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue(), ex.toString()));
    }

    public ResponseEntity<? extends BaseResponse> studentEmptyException(String phoneNumber) {
        String message = Constant.STATUS_MESSAGE.ALREADY_EXIST_STUDENT.getValue();
        String resMsg = StringUtil.replaceAll(message,"#{phoneNumber}", phoneNumber);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new CMResponse(Constant.STATUS_CODE.BAD_REQUEST_BODY.getValue(), resMsg));
    }
}
