package com.oscarhanke.mongodbpractise.repository;

import com.oscarhanke.mongodbpractise.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findByEmail(String email);
    void deleteByEmail(String email);
}
