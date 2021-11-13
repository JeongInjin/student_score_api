package com.freewheelin.student.api.dto;

import com.freewheelin.student.domain.student.Student;
import lombok.Getter;

import java.util.List;

@Getter
public class StudentListResponseDto {
    private Long id;
    private String name;
    private int age;
    private String schoolType;
    private String phoneNumber;

    public StudentListResponseDto(Student entity){
        this.id = entity.getId();
        this.name = entity.getName();
        this.age = entity.getAge();
        this.schoolType = entity.getSchoolType();
        this.phoneNumber = entity.getPhoneNumber();
    }
}

