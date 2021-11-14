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
        check = scoreParameterValidation(studentId, subjectId, score.getScore());
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
        check = scoreParameterValidation(studentId, subjectId, score.getScore());
        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없거나, 점수를 다시 한번 확인해 주세요(0~100점)");

        StSjSaveRequestDto requestDto = StSjSaveRequestDto.builder()
                .student_id(studentId)
                .subject_id(subjectId)
                .score(score.getScore())
                .build();

        return scoreService.modify(requestDto);
    }
    protected boolean scoreParameterValidation(Long studentId, Long subjectId, int score){
        boolean result;
        result = StringUtil.requiredValidationCheckReturnBoolean(studentId);
        if(result) result = StringUtil.requiredValidationCheckReturnBoolean(subjectId);
        if(result) result = StringUtil.requiredValidationCheckReturnBoolean(score);
        return result;
    }

    @Description("특정 학생, 특정 과목의 점수 삭제 합니다.")
    @DeleteMapping("/students/{studentId}/subjects/{subjectId}/scores")
    public ResponseEntity<Object> scoreDelete(@PathVariable Long studentId, @PathVariable Long subjectId){
        boolean check;
        check = scoreParameterValidation(studentId, subjectId, 0);
        if(!check) return ResPonseExceptionHandler.handleMethodArgumentNotValid("필수 값이 없습니다. 학생 아이디, 과목 아이디를 다시 확인해 주세요.");

        StSjSaveRequestDto requestDto = StSjSaveRequestDto.builder()
                .student_id(studentId)
                .subject_id(subjectId)
                .build();

        return scoreService.delete(requestDto);
    }

}
