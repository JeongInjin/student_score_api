package com.freewheelin.student.domain.student;

import com.freewheelin.student.api.dto.StudentListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Long> {

    List<Student> findByPhoneNumber(String ph);

    @Query("SELECT s FROM Student AS s ORDER BY s.id DESC")
    List<StudentListResponseDto> findAllDesc();
}
