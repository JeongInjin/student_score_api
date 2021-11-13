package com.freewheelin.student.domain.student;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

//    @AfterEach
//    public void cleanUp(){ studentRepository.deleteAll();}

    @Test
    public void Student_등록됨(){
        //given
        String name = "학생1";
        int age = 15;
        SchoolType schoolType = SchoolType.ELEMENTARY;
        String phoneNumber = "01012345678";
        studentRepository.save(Student.builder()
                .name(name)
                .age(age)
                .schoolType(schoolType)
                .phoneNumber(phoneNumber)
                .build()
        );
        //when
        List<Student> studentList = studentRepository.findAll();
        //then
        Student student = studentList.get(0);
        System.out.println("************************ createdDate = " + student.getCreatedDate() + ",   modifiedDate = " + student.getModifiedDate());
        assertThat(student.getAge()).isEqualTo(15);
        assertThat(student.getName()).isEqualTo("학생1");
        assertThat(student.getAdminId()).isEqualTo("ADMIN");
    }

}