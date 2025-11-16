package com.conestoga.scheduler.controller;

import com.conestoga.scheduler.dto.CourseDTO;
import com.conestoga.scheduler.service.CourseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@CrossOrigin(origins = "http://localhost:3000")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // Get /api/courses?userId=1
    // Get all courses for a user

    @GetMapping
    public ResponseEntity<List<CourseDTO>> getAllCourses(@RequestParam Long userId) {
        List<CourseDTO> courses = courseService.getAllCourses(userId);
        return ResponseEntity.ok(courses);
    }

    // GET /api/courses?userId=1&term=Fall 2024
    // Get courses by term

    @GetMapping(params = { "userId", "term" })
    public ResponseEntity<List<CourseDTO>> getCourseByTerm(
            @RequestParam Long userId,
            @RequestParam String term) {
        List<CourseDTO> courses = courseService.getCoursesByTerm(userId, term);
        return ResponseEntity.ok(courses);
    }

    // GET /api/courses/1?userId=1
    // Get a single course by ID

    @GetMapping("/{id}")
    public ResponseEntity<CourseDTO> getCourseById(
            @PathVariable Long id,
            @RequestParam Long userId) {
        CourseDTO course = courseService.getCourseById(id, userId);
        return ResponseEntity.ok(course);
    }

    // POST /api/courses?userId=1
    // Create a new course

    @PostMapping
    public ResponseEntity<CourseDTO> createCourse(
            @Valid @RequestBody CourseDTO courseDTO,
            @RequestParam Long userId) {
        CourseDTO createdCourse = courseService.createCourse(courseDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCourse);
    }

    // PUT /api/courses/1?userId=1
    // Update an existing course

    @PutMapping("/{id}")
    public ResponseEntity<CourseDTO> updateCourse(
            @PathVariable Long id,
            @Valid @RequestBody CourseDTO courseDTO,
            @RequestParam Long userId) {
        CourseDTO updatedCourse = courseService.updateCourse(id, courseDTO, userId);
        return ResponseEntity.ok(updatedCourse);
    }

    // DELETE /api/courses/1?userId=1
    // Delete a course

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(
            @PathVariable Long id,
            @RequestParam Long userId) {
        courseService.deleteCourse(id, userId);
        return ResponseEntity.noContent().build();
    }

    // GET /api/courses/search?code=COMP
    // Search courses by code

    @GetMapping("/search")
    public ResponseEntity<List<CourseDTO>> searchCourses(@RequestParam String code) {
        List<CourseDTO> courses = courseService.searchCourses(code);
        return ResponseEntity.ok(courses);
    }
}
