package com.freewheelin.student.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freewheelin.student.api.dto.StudentSaveRequestDto;
import com.freewheelin.student.domain.student.SchoolType;
import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.student.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private MockMvc mvc;

    @BeforeEach
    public void setUp(){
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .build();
    }

    @Test
    @WithMockUser(roles = "USER")
    public void Stuent_등록됨() throws Exception{
        //given
        String name = "학생1";
        int age = 15;
        SchoolType schoolType = SchoolType.ELEMENTARY;
        String phoneNumber = "01012345678";
        StudentSaveRequestDto requestDto = StudentSaveRequestDto.builder()
                .name(name)
                .age(age)
                .schoolType(schoolType)
                .phoneNumber(phoneNumber)
                .build();

        String url = "http://localhost:" + port + "/students";

        //when
        mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //then
        List<Student> studentList = studentRepository.findAll();
        Student student = studentList.get(0);
        System.out.println("************************ createdDate = " + student.getCreatedDate() + ",   modifiedDate = " + student.getModifiedDate());
        assertThat(student.getAge()).isEqualTo(15);
        assertThat(student.getName()).isEqualTo("학생1");
        assertThat(student.getAdminId()).isEqualTo("ADMIN");
    }
}