package com.freewheelin.student.api.dto;

import com.freewheelin.student.domain.subject.Subject;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SubjectSaveRequestDto {

    private Long id;
    @Description("과목 이름")
    private String name;

    @Builder
    public SubjectSaveRequestDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Subject toEntity(){
        return Subject.builder().name(name).build();
    }
}
