package com.oscarhanke.mongodbpractise.service;

import com.oscarhanke.mongodbpractise.DatabaseTestConfiguration;
import com.oscarhanke.mongodbpractise.model.Adress;
import com.oscarhanke.mongodbpractise.model.Gender;
import com.oscarhanke.mongodbpractise.model.Student;
import com.oscarhanke.mongodbpractise.repository.StudentRepository;
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
@Import(DatabaseTestConfiguration.class)
class StudentServiceIntegrationTest {

    @Autowired
    private StudentService studentService;

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
                "firstName1",
                "lastName1",
                "test1.email@email.com",
                Gender.MALE,
                address,
                List.of("Computer Sciencie", "Maths"),
                BigDecimal.ONE);


        Student student2 = new Student(
                "firstName2",
                "lastName2",
                "test2.email@email.com",
                Gender.MALE,
                address,
                List.of("Computer Sciencie"),
                BigDecimal.TEN);

        Student student3 = new Student(
                "firstName3",
                "lastName3",
                "test3.email@email.com",
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
    void getAllStudent() {
        System.out.println(studentService.findAll());
    }

    @Test
    void getByEmail() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void deleteAll() {
    }
}