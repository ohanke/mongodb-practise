package com.oscarhanke.mongodbpractise;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@SpringBootApplication
public class MongodbPractiseApplication {

	public static void main(String[] args) {
		SpringApplication.run(MongodbPractiseApplication.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository repository){
		return args -> {
			Adress address = new Adress(
					"Poland",
					"Gdansk",
					"123-456"
			);

		    Student student = new Student(
					"Oscar",
					"Hanke",
					"oscar.hanke@email.com",
					Gender.MALE,
					address,
					List.of("Computer Sciencie", "Maths"),
					BigDecimal.TEN,
					LocalDateTime.now());

			repository.insert(student);
		};
	}

}
