package com.freewheelin.student.api.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Score {
    private int score;
    private Long studentId;
    private Long subjectId;
}
