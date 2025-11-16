package com.conestoga.scheduler.service;

import com.conestoga.scheduler.dto.CourseDTO;
import com.conestoga.scheduler.entity.Course;
import com.conestoga.scheduler.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // Get all courses for a specific user

    public List<CourseDTO> getAllCourses(Long userId) {
        List<Course> courses = courseRepository.findByUserId(userId);
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get courses by user and term

    public List<CourseDTO> getCoursesByTerm(Long userId, String term) {
        List<Course> courses = courseRepository.findByUserIdAndTerm(userId, term);
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a single course by ID

    public CourseDTO getCourseById(Long id, Long userId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        // Security check: ensure course belongs to the user
        if (!course.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to course");
        }

        return convertToDTO(course);
    }

    // Create a new course

    @Transactional
    public CourseDTO createCourse(CourseDTO courseDTO, Long userId) {
        Course course = new Course();
        course.setCourseCode(courseDTO.getCourseCode());
        course.setCourseName(courseDTO.getCourseName());
        course.setInstructor(courseDTO.getInstructor());
        course.setTerm(courseDTO.getTerm());
        course.setUserId(userId);

        Course savedCourse = courseRepository.save(course);
        return convertToDTO(savedCourse);
    }

    // Update an existing course

    @Transactional
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO, Long userId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        // Security check
        if (!course.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to course");
        }

        // Update fields
        course.setCourseCode(courseDTO.getCourseCode());
        course.setCourseName(courseDTO.getCourseName());
        course.setInstructor(courseDTO.getInstructor());
        course.setTerm(courseDTO.getTerm());

        Course updatedCourse = courseRepository.save(course);
        return convertToDTO(updatedCourse);

    }

    // Delete a course

    @Transactional
    public void deleteCourse(Long id, Long userId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));

        // Security check
        if (!course.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to course");
        }

        courseRepository.delete(course);
    }

    // Search courses by code

    public List<CourseDTO> searchCourses(String courseCode) {
        List<Course> courses = courseRepository.findByCourseCodeContainingIgnoreCase(courseCode);
        return courses.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Convert course entity to courseDTO

    private CourseDTO convertToDTO(Course course) {
        CourseDTO dto = new CourseDTO();
        dto.setId(course.getId());
        dto.setCourseCode(course.getCourseCode());
        dto.setCourseName(course.getCourseName());
        dto.setInstructor(course.getInstructor());
        dto.setTerm(course.getTerm());
        return dto;
    }
}
