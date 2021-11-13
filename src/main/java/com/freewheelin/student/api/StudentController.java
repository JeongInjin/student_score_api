package com.freewheelin.student.api;

import com.freewheelin.student.api.common.response.BaseResponse;
import com.freewheelin.student.api.dto.StudentSaveRequestDto;
import com.freewheelin.student.service.student.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    @PostMapping("/students")
    public ResponseEntity<? extends BaseResponse> save(@Valid @RequestBody StudentSaveRequestDto requestDto){
        return studentService.save(requestDto);
    }
}
