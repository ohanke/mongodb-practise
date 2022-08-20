package com.oscarhanke.mongodbpractise.service;

import com.oscarhanke.mongodbpractise.MongodbPractiseApplicationTests;
import com.oscarhanke.mongodbpractise.model.Adress;
import com.oscarhanke.mongodbpractise.model.Gender;
import com.oscarhanke.mongodbpractise.model.Student;
import com.oscarhanke.mongodbpractise.repository.StudentRepository;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class StudentServiceUnitTest extends MongodbPractiseApplicationTests {

    List<Student> students;
    Student student;
    @InjectMocks
    StudentService studentService;
    @Mock
    StudentRepository studentRepository;
    @Mock
    MongoTemplate mongoTemplate;

    @BeforeEach
    void setUp() {
        studentService = new StudentService(studentRepository);
//        MockitoAnnotations.openMocks(this);
        setupStudents();
    }

    @AfterEach
    public void afterEach(){
        this.mongoTemplate.dropCollection("students");
    }

    @Test
//    @DisplayName("Test if getAll() is not null and contains more than one student")
    void findAll_positiveAmount() {
        //given
        when(studentRepository.findAll()).thenReturn(students);
        //when
        List<Student> studentsFromDB = studentService.findAll();
        //then
        assertNotNull(studentsFromDB);
        assertTrue(studentsFromDB.size() > 0);
        verify(studentRepository).findAll();
    }

    @Test
    void findByEmail_existingEmail_success() {
        //given
        String expectedEmail = student.getEmail();
        when(studentRepository.findByEmail(anyString())).thenReturn(Optional.of(student));

        //when
        Student studentFromDB = studentService.findByEmail(expectedEmail);

        //then
        assertNotNull(studentFromDB);
        assertEquals(expectedEmail, studentFromDB.getEmail());
        verify(studentRepository).findByEmail(anyString());
    }

    @Test
    void create_validBody_success() {
        //given
        when(studentRepository.insert(any(Student.class))).thenReturn(student);

        //then
        Student savedStudent = studentService.create(student);

        //then
        assertNotNull(savedStudent);
        verify(studentRepository).insert(any(Student.class));
    }

    @Test
    void update() {
        //given
        String firstNameBeforeUpdate = student.getFirstName();
        String updateName = "new first name";
        Student studentToBeUpdated = student;
        studentToBeUpdated.setFirstName(updateName);
        when(studentRepository.findByEmail(any())).thenReturn(Optional.of(student));
        when(studentRepository.save(any())).thenReturn(studentToBeUpdated);

        //when
        Student updatedStudent = studentService.update(student);

        //then
        assertNotNull(updatedStudent);
        assertNotEquals(firstNameBeforeUpdate, updatedStudent.getFirstName());
        assertEquals(updateName, updatedStudent.getFirstName());
        verify(studentRepository).findByEmail(anyString());
        verify(studentRepository).save(any());
    }

    @Test
    void deleteByEmail() {
        //when
        //then
        studentService.deleteByEmail(student.getEmail());
        //
        verify(studentRepository).deleteByEmail(anyString());
    }

    @Test
    void deleteAll() {
        //when
        //then
        studentService.deleteAll();
        //
        verify(studentRepository).deleteAll();
    }

    private void setupStudents() {
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

        student = student1;
        students = Arrays.asList(student1, student2, student3);
    }
}