package com.efub.dhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients("com.efub.dhs.global.feign.client")
@SpringBootApplication
public class DhsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DhsApplication.class, args);
	}

}
