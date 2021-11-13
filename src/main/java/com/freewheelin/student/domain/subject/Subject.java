package com.freewheelin.student.domain.subject;

import com.freewheelin.student.domain.BaseEntity;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Description("괴목 정보")
@Getter
@NoArgsConstructor

@Entity
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "과목 이름이 이미 있습니다.")
    @Size(min = 1, max = 12, message = "이름은 1~16자 사이로 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "과목은 1~12자, 한글/영어/숫자 포함만 가능 합니다.")
    @Description("과목 이름")
    private String name;

    @Builder
    public Subject(String name) {
        this.name = name;
    }
}
