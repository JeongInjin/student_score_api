package com.freewheelin.student.api.dto;

import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.subject.Subject;
import lombok.Builder;
import lombok.Getter;

@Getter
public class SubjectListResponseDto {
    private Long id;
    private String name;

    @Builder
    public SubjectListResponseDto(Subject entity){
        this.id = entity.getId();
        this.name = entity.getName();
    }

    public SubjectListResponseDto() {

    }
}
