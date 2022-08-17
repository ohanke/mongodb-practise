package com.oscarhanke.mongodbpractise;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudent() {
        return studentRepository.findAll();
    }

    public Student getByEmail(String email) {
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
        student.setFirstName(body.getFirstName());
        student.setLastName(body.getLastName());
        student.setEmail(body.getEmail());
        student.setGender(body.getGender());
        student.setAdress(body.getAdress());
        student.setFavouriteSubjects(body.getFavouriteSubjects());
        student.setTotalSpentInBooks(body.getTotalSpentInBooks());
        student.setCreated(body.getCreated());
        return student;
    }

    public void deleteById(String email) {
        studentRepository.deleteByEmail(email);
    }

    public void deleteAll() {
        studentRepository.deleteAll();
    }
}
