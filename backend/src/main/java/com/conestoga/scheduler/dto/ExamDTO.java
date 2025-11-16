package com.conestoga.scheduler.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalTime;

public class ExamDTO {

    private Long id;

    @NotNull(message = "Course ID is required")
    private Long courseId;

    @NotBlank(message = "Exam type is required")
    private String examType;

    @NotNull(message = "Exam date is required")
    private LocalDate examDate;

    @NotNull(message = "Exam time is required")
    private LocalTime examTime;

    private String location;

    private Double duration;

    @NotBlank(message = "Status is required")
    private String status;

    // Additional fields for response (not required for input)
    private String courseCode;
    private String courseName;

    // Constructors

    public ExamDTO() {
    }

    // Full constructor with course details
    public ExamDTO(Long id, Long courseId, String examType, LocalDate examDate,
            LocalTime examTime, String location, Double duration, String status,
            String courseCode, String courseName) {
        this.id = id;
        this.courseId = courseId;
        this.examType = examType;
        this.examDate = examDate;
        this.examTime = examTime;
        this.location = location;
        this.duration = duration;
        this.status = status;
        this.courseCode = courseCode;
        this.courseName = courseName;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getExamType() {
        return examType;
    }

    public void setExamType(String examType) {
        this.examType = examType;
    }

    public LocalDate getExamDate() {
        return examDate;
    }

    public void setExamDate(LocalDate examDate) {
        this.examDate = examDate;
    }

    public LocalTime getExamTime() {
        return examTime;
    }

    public void setExamTime(LocalTime examTime) {
        this.examTime = examTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
