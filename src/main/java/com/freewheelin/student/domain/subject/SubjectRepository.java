package com.freewheelin.student.domain.subject;

import com.freewheelin.student.api.dto.StudentListResponseDto;
import com.freewheelin.student.api.dto.SubjectListResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {

    @Query("SELECT s FROM Subject AS s ORDER BY s.id DESC")
    List<SubjectListResponseDto> findAllDesc();

    SubjectListResponseDto findByName(String name);
}
