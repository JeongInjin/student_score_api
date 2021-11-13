package com.freewheelin.student.api.dto;

import com.freewheelin.student.domain.student.SchoolType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class StudentSaveRequestDtoTest {

    @Test
    public void UserSaveRequestDTO_builder_테스트() throws Exception{
        String name = "학생테스트";
        int age = 15;
        String schoolType = "HIGH";
        String phoneNumber = "01011112222";

        StudentSaveRequestDto requestDto = StudentSaveRequestDto.builder()
                .name(name)
                .age(age)
                .schoolType(schoolType)
                .phoneNumber(phoneNumber)
                .build();
        assertThat(requestDto.getName()).isEqualTo(name);
        assertThat(requestDto.getSchoolType()).isEqualTo(schoolType);
    }
}