package com.freewheelin.student.api.controller;

import com.freewheelin.student.api.dto.SubjectSaveRequestDto;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.service.subject.SubjectService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @Description("전체 과목을 조회합니다.")
    @GetMapping("/subjects")
    public ResponseEntity<Object> findByAllSubject(){
        return subjectService.findByAllDesc();
    }

    @Description("과목을 삭제 합니다.")
    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<Object> delete(@PathVariable Long id){
        if(id <= 0 || StringUtil.isEmpty(id.toString())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
        return subjectService.delete(id);
    }
}
