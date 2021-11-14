package com.freewheelin.student.api;

import com.freewheelin.student.domain.student.Student;
import com.freewheelin.student.domain.subject.Subject;
import com.freewheelin.student.domain.subject.SubjectRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
public class jpaBulkInsertTest {

    @Autowired
    SubjectRepository subjectRepository;
    @Test
    public void bulkInsertTest_컴퓨터가_버벅이니_조금만_돌려보자(){
        List<Subject> list = new ArrayList<>(){
            {
                for(int i = 0; i< 500; i++){
                    add(Subject.builder()
                            .name("국어"+ i)
                            .build());
                }
            }
        };
        subjectRepository.saveAll(list);

        List<Subject> subjectList = subjectRepository.findAll();

        assertThat(subjectList.size()).isEqualTo(500);
        assertThat(subjectList.get(50).getName()).isEqualTo("국어50");
    }
}
