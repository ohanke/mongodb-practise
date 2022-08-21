package com.oscarhanke.mongodbpractise.repository;

import com.oscarhanke.mongodbpractise.model.Adress;
import com.oscarhanke.mongodbpractise.model.Gender;
import com.oscarhanke.mongodbpractise.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@DataMongoTest
@ExtendWith(SpringExtension.class)
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @BeforeEach
    public void beforeEach() {
        Adress address = new Adress(
                "Poland",
                "Gdansk",
                "123-456"
        );

        Student student1 = new Student(
                "Oscar",
                "Hanke",
                "oscar.hanke@email.com",
                Gender.MALE,
                address,
                List.of("Computer Sciencie", "Maths"),
                BigDecimal.ONE);


        Student student2 = new Student(
                "Marian",
                "Kowalski",
                "marian.kowalski@email.com",
                Gender.MALE,
                address,
                List.of("Computer Sciencie"),
                BigDecimal.TEN);

        Student student3 = new Student(
                "Josh",
                "Bosh",
                "josh.bosh@email.com",
                Gender.MALE,
                address,
                List.of("Maths"),
                BigDecimal.ZERO);

        List<Student> students = Arrays.asList(student1, student2, student3);
        this.studentRepository.saveAll(students);
    }

    @AfterEach
    public void afterEach(){
        this.mongoTemplate.dropCollection("students");
    }

    @Test
    void findByEmail() {
    }

    @Test
    void deleteByEmail() {
    }
}