package com.freewheelin.student.domain.stsjBridge;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.freewheelin.student.api.dto.StudentListResponseDto;
import com.freewheelin.student.api.dto.SubjectListResponseDto;
import com.freewheelin.student.domain.BaseEntity;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.subject.Subject;
import jdk.jfr.Description;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

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
    @JsonBackReference
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @Description("과목 테이블 키")
    @JsonBackReference
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Description("과목 점수")
    private int score;

    @Builder
    public StSjBridge(Long id, StudentListResponseDto studentDto, SubjectListResponseDto subjectDto, int score) {
        this.id = id;
        this.student = Student.builder()
                .id(studentDto.getId())
                .name(studentDto.getName())
                .age(studentDto.getAge())
                .schoolType(studentDto.getSchoolType())
                .phoneNumber(studentDto.getPhoneNumber())
                .build();
        this.subject = Subject.builder()
                .id(subjectDto.getId())
                .name(subjectDto.getName())
                .build();
        this.score = score;
    }
    @Builder
    public StSjBridge(Long studentId, Long subjectId, int score){
//        this.student = new Student().builder()
//                .id(studentId)
//                .build();
//        this.subject = Subject.builder()
//                .id(subjectId)
//                .build();
        this.student = new Student(studentId);
        this.subject = new Subject(subjectId);
        this.score = score;
    }
    public StSjBridge(int score) {
        this.score = score;
    }
}
