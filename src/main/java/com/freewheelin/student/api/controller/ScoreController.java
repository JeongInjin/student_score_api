package com.freewheelin.student.api.controller;

import com.freewheelin.student.api.common.response.ResPonseExceptionHandler;
import com.freewheelin.student.api.dto.Score;
import com.freewheelin.student.api.dto.StSjSaveRequestDto;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.service.score.ScoreService;
import jdk.jfr.Description;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScoreController {
    private final ScoreService scoreService;

    @Description("특정학생, 특정 과목에 점수 할당 합니다.")
    @PostMapping("/students/{studentId}/subjects/{subjectId}/scores")
    public ResponseEntity<Object> scoreSave(@PathVariable Long studentId, @PathVariable Long subjectId, @RequestBody Score score){
        boolean check;
        check = scoreParameterValidation(studentId);
        if(check) scoreParameterValidation(subjectId);
        if(check) scoreParameterValidation(score.getScore());
        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없거나, 점수를 다시 한번 확인해 주세요(0~100점)");

        StSjSaveRequestDto requestDto = StSjSaveRequestDto.builder()
                .student_id(studentId)
                .subject_id(subjectId)
                .score(score.getScore())
                .build();

        return scoreService.save(requestDto);
    }

    @Description("특정 학생, 특정 과목의 점수 수정 합니다.")
    @PutMapping("/students/{studentId}/subjects/{subjectId}/scores")
    public ResponseEntity<Object> scoreModify(@PathVariable Long studentId, @PathVariable Long subjectId, @RequestBody Score score){
        boolean check;
        check = scoreParameterValidation(studentId);
        if(check) scoreParameterValidation(subjectId);
        if(check) scoreParameterValidation(score.getScore());

        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없거나, 점수를 다시 한번 확인해 주세요(0~100점)");

        StSjSaveRequestDto requestDto = StSjSaveRequestDto.builder()
                .student_id(studentId)
                .subject_id(subjectId)
                .score(score.getScore())
                .build();

        return scoreService.modify(requestDto);
    }
    protected boolean scoreParameterValidation(Object v){
        boolean result;
        result = StringUtil.requiredValidationCheckReturnBoolean(v);
        return result;
    }

    @Description("특정 학생, 특정 과목의 점수 삭제 합니다.")
    @DeleteMapping("/students/{studentId}/subjects/{subjectId}/scores")
    public ResponseEntity<Object> scoreDelete(@PathVariable Long studentId, @PathVariable Long subjectId){
        boolean check;
        check = scoreParameterValidation(studentId);
        if(check) scoreParameterValidation(subjectId);

        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없습니다. 학생 아이디, 과목 아이디를 다시 확인해 주세요.");

        StSjSaveRequestDto requestDto = StSjSaveRequestDto.builder()
                .student_id(studentId)
                .subject_id(subjectId)
                .build();

        return scoreService.delete(requestDto);
    }

    @Description("특정 학생의 평균 점수 조회 합니다.")
    @GetMapping("/students/{studentId}/average-score")
    public ResponseEntity<Object> scoreStudentAvg(@PathVariable Long studentId){
        boolean check;
        check = scoreParameterValidation(studentId);
        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없습니다. 학생 아이디를 다시 확인해 주세요.");

        return scoreService.studentScoreAvg(studentId);
    }

    @Description("특정 과목에 대한 전체 학생들의 평균 점수 조회 합니다.")
    @GetMapping("/subjects/{subjectId}/average-score")
    public ResponseEntity<Object> scoreSubjectAvg(@PathVariable Long subjectId){
        boolean check;
        check = scoreParameterValidation(subjectId);
        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없습니다. 과목 아이디를 다시 확인해 주세요.");

        return scoreService.subjectScoreAvg(subjectId);
    }
}
