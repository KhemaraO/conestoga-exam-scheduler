package com.conestoga.scheduler.service;

import com.conestoga.scheduler.dto.ExamDTO;
import com.conestoga.scheduler.entity.Course;
import com.conestoga.scheduler.entity.Exam;
import com.conestoga.scheduler.entity.ExamStatus;
import com.conestoga.scheduler.repository.CourseRepository;
import com.conestoga.scheduler.repository.ExamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExamService {

    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public ExamService(ExamRepository examRepository, CourseRepository courseRepository) {
        this.examRepository = examRepository;
        this.courseRepository = courseRepository;
    }

    // Get all exams for a year

    public List<ExamDTO> getAllExams(Long userId) {
        List<Exam> exams = examRepository.findByCourseUserId(userId);
        return exams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get exams by status (UPCOMING or COMPLETED)

    public List<ExamDTO> getExamsByStatus(Long userId, String status) {
        ExamStatus examStatus = ExamStatus.valueOf(status.toUpperCase());
        List<Exam> exams = examRepository.findByCourseUserIdAndStatus(userId, examStatus);
        return exams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get current date exam

    public List<ExamDTO> getTodaysExams(Long userId) {
        List<Exam> exams = examRepository.findTodaysExams(userId, LocalDate.now());
        return exams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get exams within a date range

    public List<ExamDTO> getExamsInDateRange(Long userId, LocalDate startDate, LocalDate endDate) {
        List<Exam> exams = examRepository.findByCourseUserIdAndExamDateBetween(userId, startDate, endDate);
        return exams.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Get a single exam by ID

    public ExamDTO getExamById(Long id, Long userId) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

        // Security check: ensure exam's course belongs to the user
        if (!exam.getCourse().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to exam");
        }

        return convertToDTO(exam);
    }

    // Create a new exam

    @Transactional
    public ExamDTO createExam(ExamDTO examDTO, Long userId) {
        // Get the course and verify it belongs to the user
        Course course = courseRepository.findById(examDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + examDTO.getCourseId()));

        if (!course.getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized: Course does not belong to user");
        }

        // Create the exam
        Exam exam = new Exam();
        exam.setCourse(course);
        exam.setExamType(examDTO.getExamType());
        exam.setExamDate(examDTO.getExamDate());
        exam.setExamTime(examDTO.getExamTime());
        exam.setLocation(examDTO.getLocation());
        exam.setDuration(examDTO.getDuration());
        exam.setStatus(ExamStatus.valueOf(examDTO.getStatus().toUpperCase()));

        Exam savedExam = examRepository.save(exam);
        return convertToDTO(savedExam);
    }

    // Update an existing exam

    @Transactional
    public ExamDTO updateExam(Long id, ExamDTO examDTO, Long userId) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

        // Security check
        if (!exam.getCourse().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to exam");
        }

        // Update fields
        exam.setExamType(examDTO.getExamType());
        exam.setExamDate(examDTO.getExamDate());
        exam.setExamTime(examDTO.getExamTime());
        exam.setLocation(examDTO.getLocation());
        exam.setDuration(examDTO.getDuration());
        exam.setStatus(ExamStatus.valueOf(examDTO.getStatus().toUpperCase()));

        Exam updatedExam = examRepository.save(exam);
        return convertToDTO(updatedExam);
    }

    // Update only the exam status (for marking as completed)

    @Transactional
    public ExamDTO updateExamStatus(Long id, String status, Long userId) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

        // Security check
        if (!exam.getCourse().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to exam");
        }

        exam.setStatus(ExamStatus.valueOf(status.toUpperCase()));
        Exam updatedExam = examRepository.save(exam);
        return convertToDTO(updatedExam);
    }

    // Delete an exam

    @Transactional
    public void deleteExam(Long id, Long userId) {
        Exam exam = examRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Exam not found with id: " + id));

        // Security check
        if (!exam.getCourse().getUserId().equals(userId)) {
            throw new RuntimeException("Unauthorized access to exam");
        }

        examRepository.delete(exam);
    }

    // Convert exam entity to examDTO

    private ExamDTO convertToDTO(Exam exam) {
        ExamDTO dto = new ExamDTO();
        dto.setId(exam.getId());
        dto.setCourseId(exam.getCourse().getId());
        dto.setCourseCode(exam.getCourse().getCourseCode());
        dto.setCourseName(exam.getCourse().getCourseName());
        dto.setExamType(exam.getExamType());
        dto.setExamDate(exam.getExamDate());
        dto.setExamTime(exam.getExamTime());
        dto.setLocation(exam.getLocation());
        dto.setDuration(exam.getDuration());
        dto.setStatus(exam.getStatus().name().toLowerCase());
        return dto;
    }
}
