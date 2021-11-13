package com.freewheelin.student.domain.stsjBridge;

import com.freewheelin.student.domain.BaseEntity;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.subject.Subject;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Description("학생이 듣는 과목, 학생(1) : (N)과목(N) : 과목(1) 로 구성되어있음.")
@RequiredArgsConstructor
@Getter
@Entity
public class StSjBridge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stSjBridge_id")
    private Long id;

    @ManyToOne
    @Description("학생 테이블 키")
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @Description("과목 테이블 키")
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Description("과목 점수")
    private int score;

    @Builder
    public StSjBridge(Long id, Student student, Subject subject, int score) {
        this.id = id;
        this.student = student;
        this.subject = subject;
        this.score = score;
    }
}
