package com.freewheelin.student.api.controller;

import com.freewheelin.student.api.dto.StudentSaveRequestDto;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.service.student.StudentService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class StudentController {

    private final StudentService studentService;

    /* POST / JSON
    http://localhost:8080/students
    {
        "name": "aAzZ이ㅏ",
        "age": 19,
        "schoolType": "HIGH",
        "phoneNumber": "010-1234-5678"
    }
    * */
    @Description("학생을 추가 합니다.")
    @PostMapping("/students")
    public ResponseEntity<Object> save(@Valid @RequestBody StudentSaveRequestDto requestDto){
        return studentService.save(requestDto);
    }

    @Description("전체 학생을 조회합니다.")
    @GetMapping("/students")
    public ResponseEntity<Object> findByAllStudent(){
        return studentService.findByAllDesc();
    }

    @Description("학생을 삭제 합니다.")
    @DeleteMapping("/students/{studentId}")
    public ResponseEntity<Object> delete(@PathVariable Long studentId){
        if(studentId <= 0 || StringUtil.isEmpty(studentId.toString())) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        }
        return studentService.delete(studentId);
    }
}
