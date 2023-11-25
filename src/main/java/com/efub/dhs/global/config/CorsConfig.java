package com.efub.dhs.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
			.allowedOrigins("http://api.daehaengsa.kro.kr",
				"https://api.daehaengsa.kro.kr",
				"http://localhost:3000",
				"https://dhs-frontend.vercel.app",
				"https://daehaengsa.kro.kr")
			.allowedMethods("GET", "POST", "PATCH", "DELETE")
			.allowCredentials(true)
			.maxAge(3000);
	}
}
