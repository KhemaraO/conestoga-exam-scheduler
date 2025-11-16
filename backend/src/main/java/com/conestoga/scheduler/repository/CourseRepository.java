package com.conestoga.scheduler.repository;

import com.conestoga.scheduler.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    // Find all courses for a specific user
    List<Course> findByUserId(Long userId);

    // Find courses by user and term
    List<Course> findByUserIdAndTerm(Long userId, String term);

    // Find courses by course code (for searching)
    List<Course> findByCourseCodeContainingIgnoreCase(String courseCode);
}
