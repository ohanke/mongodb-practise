package com.oscarhanke.mongodbpractise.config;

import com.oscarhanke.mongodbpractise.model.Adress;
import com.oscarhanke.mongodbpractise.model.Gender;
import com.oscarhanke.mongodbpractise.model.Student;
import com.oscarhanke.mongodbpractise.repository.StudentRepository;
import com.oscarhanke.mongodbpractise.service.StudentQueries;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ContextRefreshedEvent> {

    private final StudentRepository studentRepository;
    private final StudentQueries studentQueries;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Adress address = new Adress(
                "Poland",
                "Gdansk",
                "123-456"
        );

        createIfDeosntExist(new Student(
                "Oscar",
                "Hanke",
                "oscar.hanke@email.com",
                Gender.MALE,
                address,
                List.of("Computer Sciencie", "Maths"),
                BigDecimal.TEN));


        createIfDeosntExist(new Student(
                "Marian",
                "Kowalski",
                "marian.kowalski@email.com",
                Gender.MALE,
                address,
                List.of("Computer Sciencie"),
                BigDecimal.TEN));

        createIfDeosntExist(new Student(
                "Josh",
                "Bosh",
                "josh.bosh@email.com",
                Gender.MALE,
                address,
                List.of("Maths"),
                BigDecimal.TEN));


        List<Student> allStudents = this.studentQueries.findAll("firstName", 0, 3);
        log.info("StudentQueries.findAll() result");
        System.out.println(allStudents);

        log.info("StudentQueries.findSingleById() result");
        Student byId = this.studentQueries.findSingleById("62fe2f083de3924a48a3c8cb");
        System.out.println(byId);

        log.info("StudentQueries.countMales() result");
        long males = this.studentQueries.countMales();
        System.out.println(males);

        log.info("StudentQueries.findByFavouriteSubjects() result");
        List<Student> bySubjects = this.studentQueries.findByFavouriteSubjects("Maths");
        System.out.println(bySubjects);

        log.info("studentQueries.findByFreeText");
        List<Student> studentByFreeText = studentQueries.findByFreeText("Maths");
        System.out.println(studentByFreeText);

//			usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);
    }

    private void createIfDeosntExist(Student student) {
        studentRepository.findByEmail(student.getEmail()).ifPresentOrElse(
                s -> log.warn("{} already exists", s.getEmail()),
                () -> {
                    studentRepository.insert(student);
                    log.info("Created student with email: {}", student.getEmail());
                }
        );
    }

//    private static void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
//        Query query = new Query();
//        query.addCriteria(Criteria.where("email").is(email));
//        List<Student> students = mongoTemplate.find(query, Student.class);
//
//        if (students.size() > 1){
//            throw new IllegalStateException("Found another student with email: " + email);
//        }
//
//        if (students.isEmpty()){
//            System.out.println("Inserting student: " + student);
//            repository.insert(student);
//        } else {
//            System.out.println(student + " already exists");
//        }
//    }
}
