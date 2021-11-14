package com.freewheelin.student.api.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ScoreStudentResponse {
    private Long student_id;
    private String name;
    private int score;

    public ScoreStudentResponse(Long student_id, String name, int score) {
        this.student_id = student_id;
        this.name = name;
        this.score = score;
    }
}
