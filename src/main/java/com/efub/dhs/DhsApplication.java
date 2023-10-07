package com.efub.dhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class DhsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DhsApplication.class, args);
	}

}
