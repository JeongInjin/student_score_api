package com.freewheelin.student.api.dto;

import com.freewheelin.student.domain.subject.Subject;
import jdk.jfr.Description;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class SubjectSaveRequestDto {
    @NotNull(message = "과목 이름이 이미 있습니다.")
    @Size(min = 1, max = 12, message = "과목은 1~12자 사이로 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "과목은 1~12자, 한글/영어/숫자 포함만 가능 합니다.")
    @Description("과목 이름")
    private String name;

    public Subject toEntity(){
        return Subject.builder().name(name).build();
    }
}
