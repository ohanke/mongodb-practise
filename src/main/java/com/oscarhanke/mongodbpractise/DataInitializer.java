package com.oscarhanke.mongodbpractise;

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
