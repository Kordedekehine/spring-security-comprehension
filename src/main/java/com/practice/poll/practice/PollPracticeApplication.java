package com.practice.poll.practice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
//We’ll need to register JPA 2.1 converters so that all the Java 8 Date/Time fields in the domain models
// automatically get converted to PostgresSQL types when we persist them in the database.
@EntityScan(basePackageClasses = {
		PollPracticeApplication.class,
		Jsr310JpaConverters.class
})
public class PollPracticeApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	public static void main(String[] args) {
		SpringApplication.run(PollPracticeApplication.class, args);
	}

}
