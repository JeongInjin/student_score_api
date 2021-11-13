package com.freewheelin.student.api.dto;

import com.freewheelin.student.api.util.EnumPattern;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.domain.BaseEntity;
import com.freewheelin.student.domain.student.Student;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Getter
@NoArgsConstructor
public class StudentSaveRequestDto extends BaseEntity {
    @NotNull(message = "학생 이름이 없습니다.")
    @Size(min = 1, max = 16, message = "이름은 1~16자 사이로 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "이름은 1~16자, 한글/영어/숫자 포함만 가능 합니다.")
    @Description("학생 이름")
    private String name;

    @NotNull(message = "학생 나이가 없습니다.")
    @Description("학생 나이")
    @Min(value = 8, message = "최소 8살 이상 입력해주세요.")
    @Max(value = 19, message = "최대 19살 이하로 입력해주세요.")
    private int age;

    @NotNull(message = "학생 학교급이 없습니다.")
    @EnumPattern
    @Size(max = 10, message = "학생 학교급를 다시 확인해 주세요.")
    @Description("학생 학교급")
    private String schoolType;

    @NotNull(message = "학생 전화번호가 없습니다.")
    @Size(min = 11, max = 13, message = "전화번호가 올바르지 않습니다.")
    private String phoneNumber;

    @Builder
    public StudentSaveRequestDto(String name, int age, String schoolType, String phoneNumber) {
        this.name = name;
        this.age = age;
        this.schoolType = schoolType.toUpperCase();
        this.phoneNumber = phoneNumber;
    }

    public Student toEntity(){
        return Student.builder()
                .name(name)
                .age(age)
                .schoolType(schoolType.toUpperCase())
                .phoneNumber(StringUtil.formatPhoneNumber(phoneNumber))
                .build();
    }
}
