package com.example.studentfinal.service;

import com.example.studentfinal.entity.Student;
import com.example.studentfinal.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class StudentService {

    private final StudentRepository repo;

    // CONSTRUCTOR INJECTION (VERY IMPORTANT)
    public StudentService(StudentRepository repo) {
        this.repo = repo;
    }

    // CREATE
    public Student saveStudent(Student student) {
        return repo.save(student);
    }

    // UPDATE
    public Student updateStudent(Long id, Student student) {
        Student existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        existing.setStudentId(student.getStudentId());
        existing.setName(student.getName());
        existing.setEmail(student.getEmail());
        existing.setPhone(student.getPhone());
        existing.setCourse(student.getCourse());

        return repo.save(existing);
    }

    // SOFT DELETE (NOT REMOVED FROM DB)
    public void softDelete(Long id) {
        Student student = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        student.setStatus("DELETED");
        repo.save(student);
    }

    public Page<Student> getActiveStudents(int page, int size) {
        return repo.findByStatus("ACTIVE", PageRequest.of(page, size));
    }
}
