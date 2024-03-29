package com.freewheelin.student.domain.stsjBridge;

import com.freewheelin.student.api.dto.ScoreStudentResponseInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface StSjBridgeRepository extends JpaRepository<StSjBridge, Long> {

    @Transactional
    @Query("SELECT s FROM Student AS s WHERE s.id = ?1")
    StSjBridge findByStudentId(Long studentId);

    @Transactional
    @Query("SELECT s.id AS id, s.subject.name AS name, s.score AS score FROM StSjBridge AS s WHERE s.student.id = ?1")
    List<ScoreStudentResponseInterface> findByStudentAllSubject(Long studentId);

    @Transactional
    @Query("SELECT s.student.id AS id, s.subject.name AS name, s.score AS score FROM StSjBridge AS s WHERE s.subject.id = ?1")
    List<ScoreStudentResponseInterface> findByAllSubjectScoreAvg(Long subjectId);

    @Transactional
    @Modifying
    @Query("DELETE FROM StSjBridge s WHERE s.student.id= ?1")
    void deleteBystudentId(Long studentId);

    @Transactional
    @Query("SELECT s FROM StSjBridge AS s WHERE s.student.id = ?1 AND s.subject.id = ?2")
    Optional<StSjBridge> findByStIdSjId(Long studentId, Long subjectId);

    @Transactional
    @Modifying
    @Query("UPDATE StSjBridge s SET s.score = ?1 WHERE s.id = ?2")
    void saveSql(int score, Long stSjBridge_id);

    @Transactional
    @Modifying
    @Query("DELETE FROM StSjBridge s WHERE s.student.id= ?1 AND s.subject.id = ?2")
    void deleteByStIdSjId(Long student_id, Long subject_id);


}
