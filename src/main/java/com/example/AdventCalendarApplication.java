package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com"} , exclude = JpaRepositoriesAutoConfiguration.class)
@ComponentScan("com")
@EnableJpaRepositories("com.example.repository")
public class AdventCalendarApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdventCalendarApplication.class, args);
	}
}
