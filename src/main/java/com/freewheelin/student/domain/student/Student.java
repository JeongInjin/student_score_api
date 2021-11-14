package com.freewheelin.student.domain.student;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.freewheelin.student.api.util.EnumPattern;
import com.freewheelin.student.api.util.StringUtil;
import com.freewheelin.student.domain.BaseEntity;
import com.freewheelin.student.domain.stsjBridge.StSjBridge;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Description("학생 정보")
@Getter
@NoArgsConstructor
@Entity
public class Student extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long id;

    @Description("학생 이름")
    @NotNull(message = "학생 이름이 없습니다.")
    @Size(min = 1, max = 16, message = "이름은 1~16자 사이로 입력해주세요.")
    @Column(length = 16)
    private String name;

    @Description("학생 나이")
    @NotNull(message = "학생 나이가 없습니다.")
    @Min(value = 8, message = "최소 8살 이상 입력해주세요.")
    @Max(value = 19, message = "최대 19살 이하로 입력해주세요.")
    private int age;

    @Description("학생 학교급")
    @EnumPattern
    @Size(max = 10, message = "학생 학교급를 다시 확인해 주세요.")
    @NotNull(message = "학생 학교급이 없습니다.")
    private String schoolType;

    @NotNull(message = "학생 전화번호가 없습니다.")
    @Size(min = 10, max = 13, message = "전화번호가 올바르지 않습니다.")
    @Column(length = 13)
    private String phoneNumber;

    @OneToMany(mappedBy = "student",cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<StSjBridge> stSjBridgeList = new ArrayList<>();

    @Builder
    public Student(String name, int age, String schoolType, String phoneNumber, Long id){
        this.name = name;
        this.age = age;
        this.schoolType = schoolType;
        this.phoneNumber = StringUtil.formatPhoneNumber(phoneNumber);
        this.id = id;
    }
    @Builder
    public Student(Long id){
        this.id = id;
    }
}
