package com.freewheelin.student.api.dto;

import com.freewheelin.student.domain.stsjBridge.StSjBridge;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.subject.Subject;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@RequiredArgsConstructor
@Getter
public class StSjSaveRequestDto {
    private Long stSjBridge_id;

    private Long student_id;

    private Long subject_id;

    private int score;

    @Builder
    public StSjSaveRequestDto(Long stSjBridge_id, Long student_id, Long subject_id, int score) {
        this.stSjBridge_id = stSjBridge_id;
        this.student_id = student_id;
        this.subject_id = subject_id;
        this.score = score;
    }

    public StSjBridge toEntity(Long subject_id, Long subjectId, int score){
        return new StSjBridge(stSjBridge_id, this.subject_id, this.score);
    }

    public void setStSjBridge_id(Long stSjBridge_id) {
        this.stSjBridge_id = stSjBridge_id;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
