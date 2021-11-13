package com.freewheelin.student.api.controller;

import com.freewheelin.student.api.dto.SubjectSaveRequestDto;
import com.freewheelin.student.service.subject.SubjectService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class SubjectController {
    private final SubjectService subjectService;

    @Description("과목을 추가합니다.")
    @PostMapping("/subjects")
    public ResponseEntity<Object> save(@Valid @RequestBody SubjectSaveRequestDto requestDto){
        return subjectService.save(requestDto);
    }
}
