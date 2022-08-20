package com.oscarhanke.mongodbpractise.service;

import com.oscarhanke.mongodbpractise.DatabaseTestConfiguration;
import com.oscarhanke.mongodbpractise.MongodbPractiseApplicationTests;
import com.oscarhanke.mongodbpractise.model.Adress;
import com.oscarhanke.mongodbpractise.model.Gender;
import com.oscarhanke.mongodbpractise.model.Student;
import com.oscarhanke.mongodbpractise.repository.StudentRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@DataMongoTest
//@ExtendWith(SpringExtension.class)
//@Import(DatabaseTestConfiguration.class)
class StudentServiceIntegrationTest extends MongodbPractiseApplicationTests{

    String sampleEmail;
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

        sampleEmail = student1.getEmail();
        List<Student> students = Arrays.asList(student1, student2, student3);
        this.studentRepository.saveAll(students);
    }

    @AfterEach
    public void afterEach(){
        this.mongoTemplate.dropCollection("students");
    }

    @Test
    void getAllStudent() {
        //given
        //when
        List<Student> students = studentService.findAll();

        //then
        assertNotNull(students);
        assertTrue(students.size() > 0);
    }

    @Test
    void getByEmail() {
        //given
        //when
        Student studentFromDB = studentService.findByEmail(sampleEmail);

        //then
        assertNotNull(studentFromDB);
        assertEquals(sampleEmail, studentFromDB.getEmail());
    }

    @Test
    void create() {
        //given
        Student studentToSave = new Student(
                "sampleFirstName",
                "sampleLastName",
                "sample.email@email.com",
                Gender.FEMALE,
                new Adress(
                        "Sample",
                        "Sample",
                        "123-456"
                ),
                List.of("Computer Sciencie", "Maths"),
                BigDecimal.ONE);

        //when
        Student savedStudent = studentService.create(studentToSave);

        //then
        assertNotNull(savedStudent);
        assertNotNull(savedStudent.getId());
        assertEquals(studentToSave.getEmail(), savedStudent.getEmail());
    }

    @Test
    void update() {
        //given
        Optional<Student> studentToBeUpdated = studentRepository.findAll()
                .stream().findFirst();

        Student updateBody = new Student(
                "sampleFirstName",
                "sampleLastName",
                studentToBeUpdated.get().getEmail(),
                Gender.FEMALE,
                new Adress(
                        "Sample",
                        "Sample",
                        "123-456"
                ),
                List.of("Computer Sciencie", "Maths"),
                BigDecimal.ONE);

        //when
        Student updatedStudent = studentService.update(studentToBeUpdated.get());

        //then
        assertNotNull(updatedStudent);
        assertEquals(studentToBeUpdated.get().getFirstName(), updatedStudent.getFirstName());
    }

    @Test
    void deleteByEmail() {
        //given
        Optional<Student> studentToBeDeleted = studentRepository.findAll().stream()
                .findFirst();
        String email = studentToBeDeleted.get().getEmail();

        //when
        studentService.deleteByEmail(email);

        //then
        assertTrue(studentRepository.findByEmail(email).isEmpty());
    }

    @Test
    void deleteAll() {
        //given
        //when
        studentService.deleteAll();

        //then
        List<Student> studentsFromDB = studentRepository.findAll();
        assertTrue(studentsFromDB.isEmpty());
    }
}