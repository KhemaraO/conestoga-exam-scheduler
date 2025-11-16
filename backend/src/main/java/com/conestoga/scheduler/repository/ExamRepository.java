package com.conestoga.scheduler.repository;

import com.conestoga.scheduler.entity.Exam;
import com.conestoga.scheduler.entity.ExamStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {

    // Find all exams for a user (through course relationships)
    @Query("SELECT e FROM Exam e WHERE e.course.userId = :userId ORDER BY e.examDate ASC")
    List<Exam> findByCourseUserId(@Param("userId") Long userId);

    // Find exams by status for a user
    @Query("SELECT e FROM Exam e WHERE e.course.userId = :userId AND e.status = :status ORDER BY e.examDate ASC")
    List<Exam> findByCourseUserIdAndStatus(@Param("userId") Long userId, @Param("status") ExamStatus status);

    // Find upcoming exams within a date range
    @Query("SELECT e FROM Exam e WHERE e.course.userId = :userId AND e.examDate BETWEEN :startDate AND :endDate ORDER BY e.examDate ASC")
    List<Exam> findByCourseUserIdAndExamDateBetween(
            @Param("userId") Long userId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    // Find all exams for a specific course
    List<Exam> findByCourseId(Long courseId);

    // Find today's exams for a user
    @Query("SELECT e FROM Exam e WHERE e.course.userId = :userId AND e.examDate = :today ORDER BY e.examTime ASC")
    List<Exam> findTodaysExams(@Param("userId") Long userId, @Param("today") LocalDate today);
}
