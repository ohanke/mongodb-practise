package com.oscarhanke.mongodbpractise.service;

import com.oscarhanke.mongodbpractise.model.Student;
import com.oscarhanke.mongodbpractise.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    public Student findByEmail(String email) {
        return studentRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Student with email: " + email + " not found"));
    }

    public Student create(Student body) {
        if (studentRepository.findByEmail(body.getEmail()).isPresent()){
            throw new IllegalStateException("Student with email: " + body.getEmail() + " already exists");
        } else {
            Student student = new Student(
                    body.getFirstName(),
                    body.getLastName(),
                    body.getEmail(),
                    body.getGender(),
                    body.getAdress(),
                    body.getFavouriteSubjects(),
                    body.getTotalSpentInBooks()
            );
            return studentRepository.insert(student);
        }
    }

    public Student update(Student body) {
        return studentRepository.findByEmail(body.getEmail())
                .map(student -> performUpdate(body, student))
                .orElseThrow(() -> new IllegalStateException("Student with email: " + body.getEmail() + " not found"));
    }

    private Student performUpdate(Student body, Student student) {
        if (body.getFirstName() != null)
            student.setFirstName(body.getFirstName());
        if (body.getLastName() != null)
            student.setLastName(body.getLastName());
        if (body.getGender() != null)
            student.setGender(body.getGender());
        if (body.getAdress() != null)
            student.setAdress(body.getAdress());
        if (body.getFavouriteSubjects() != null)
            student.setFavouriteSubjects(body.getFavouriteSubjects());
        if (body.getTotalSpentInBooks() != null)
            student.setTotalSpentInBooks(body.getTotalSpentInBooks());
        if (body.getCreated() != null)
            student.setCreated(body.getCreated());
        return studentRepository.save(student);
    }

    public void deleteByEmail(String email) {
        studentRepository.deleteByEmail(email);
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }
}
