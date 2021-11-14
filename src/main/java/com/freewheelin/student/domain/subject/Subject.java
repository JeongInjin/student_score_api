package com.freewheelin.student.domain.subject;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.freewheelin.student.domain.BaseEntity;
import com.freewheelin.student.domain.stsjBridge.StSjBridge;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Description("괴목 정보")
@Getter
@NoArgsConstructor

@Entity
public class Subject extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    private Long id;

    @NotNull(message = "과목 이름이 이미 있습니다.")
    @Size(min = 1, max = 12, message = "이름은 1~16자 사이로 입력해주세요.")
    @Pattern(regexp = "^[0-9a-zA-Z가-힣]*$", message = "과목은 1~12자, 한글/영어/숫자 포함만 가능 합니다.")
    @Description("과목 이름")
    private String name;

    @OneToMany(mappedBy = "subject",cascade = CascadeType.REMOVE)
    @JsonBackReference
    private List<StSjBridge> stSjBridgeList = new ArrayList<>();

    @Builder
    public Subject(String name, Long id) {
        this.id = id;
        this.name = name;
    }
    public Subject(Long id){
        this.id = id;
    }
}
