package com.oscarhanke.mongodbpractise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class MongodbPractiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbPractiseApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository, MongoTemplate mongoTemplate){
		return args -> {
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
					BigDecimal.TEN
			);
			insertUser(repository, student1.getEmail(), student1);

			Student student2 = new Student(
					"Marian",
					"Kowalski",
					"marian.kowalski@email.com",
					Gender.MALE,
					address,
					List.of("Computer Sciencie"),
					BigDecimal.TEN
			);
			insertUser(repository, student2.getEmail(), student2);

			Student student3 = new Student(
					"Josh",
					"Bosh",
					"josh.bosh@email.com",
					Gender.MALE,
					address,
					List.of("Maths"),
					BigDecimal.TEN
			);
			insertUser(repository, student3.getEmail(), student3);

//			usingMongoTemplateAndQuery(repository, mongoTemplate, email, student);
		};
	}

	private void insertUser(StudentRepository repository, String email, Student student) {
		repository.findByEmail(email).ifPresentOrElse(
				s -> System.out.println(s + " already exists"),
				() -> {
					System.out.println("Inserting student: " + student);
					repository.insert(student);
				}
		);
	}

	private static void usingMongoTemplateAndQuery(StudentRepository repository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students = mongoTemplate.find(query, Student.class);

		if (students.size() > 1){
			throw new IllegalStateException("Found another student with email: " + email);
		}

		if (students.isEmpty()){
			System.out.println("Inserting student: " + student);
			repository.insert(student);
		} else {
			System.out.println(student + " already exists");
		}
	}

}
