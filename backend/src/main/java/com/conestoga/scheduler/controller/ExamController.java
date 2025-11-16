package com.conestoga.scheduler.controller;

import com.conestoga.scheduler.dto.ExamDTO;
import com.conestoga.scheduler.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamController {

    private final ExamService examService;

    @Autowired
    public ExamController(ExamService examService) {
        this.examService = examService;
    }

    // GET /api/exams?userId=1
    // GET /api/exams?userId=1&status=upcoming
    // Get all exams for a user

    @GetMapping
    public ResponseEntity<List<ExamDTO>> getAllExams(
            @RequestParam Long userId,
            @RequestParam(required = false) String status) {
        if (status != null && !status.isEmpty()) {
            // Filter by status if provided
            List<ExamDTO> exams = examService.getExamsByStatus(userId, status);
            return ResponseEntity.ok(exams);
        } else {
            // Get all exams
            List<ExamDTO> exams = examService.getAllExams(userId);
            return ResponseEntity.ok(exams);
        }
    }

    // GET /api/exams/today?userId=1
    // Get current date's exams

    @GetMapping("/today")
    public ResponseEntity<List<ExamDTO>> getTodaysExams(@RequestParam Long userId) {
        List<ExamDTO> exams = examService.getTodaysExams(userId);
        return ResponseEntity.ok(exams);
    }

    // GET /api/exams/range?userId=1&startDate=2024-11-01&endDate=2024-11-30
    // Get exams in a date range

    @GetMapping("/range")
    public ResponseEntity<List<ExamDTO>> getExamsInRange(
            @RequestParam Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<ExamDTO> exams = examService.getExamsInDateRange(userId, startDate, endDate);
        return ResponseEntity.ok(exams);
    }

    // GET /api/exams/1?userId=1
    // Get a single exam by ID

    @GetMapping("/{id}")
    public ResponseEntity<ExamDTO> getExamById(
            @PathVariable Long id,
            @RequestParam Long userId) {
        ExamDTO exam = examService.getExamById(id, userId);
        return ResponseEntity.ok(exam);
    }

    // POST /api/exams?userId=1
    // Create a new exam

    @PostMapping
    public ResponseEntity<ExamDTO> createExam(
            @Valid @RequestBody ExamDTO examDTO,
            @RequestParam Long userId) {
        ExamDTO createdExam = examService.createExam(examDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdExam);
    }

    // PUT /api/exams/1?userId=1
    // Update an existing exam

    @PutMapping("/{id}")
    public ResponseEntity<ExamDTO> updateExam(
            @PathVariable Long id,
            @Valid @RequestBody ExamDTO examDTO,
            @RequestParam Long userId) {
        ExamDTO updatedExam = examService.updateExam(id, examDTO, userId);
        return ResponseEntity.ok(updatedExam);
    }

    // PATCH /api/exams/1/status?userId=1&status=completed
    // Update only the exam status (for marking as completed)

    @PatchMapping("/{id}/status")
    public ResponseEntity<ExamDTO> updateExamStatus(
            @PathVariable Long id,
            @RequestParam String status,
            @RequestParam Long userId) {
        ExamDTO updatedExam = examService.updateExamStatus(id, status, userId);
        return ResponseEntity.ok(updatedExam);
    }

    // DELETE /api/exams/1?userId=1
    // Delete an exam

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExam(
            @PathVariable Long id,
            @RequestParam Long userId) {
        examService.deleteExam(id, userId);
        return ResponseEntity.noContent().build();
    }
}
