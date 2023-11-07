package com.efub.dhs.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
@OpenAPIDefinition(servers = {
	@Server(url = "http://localhost:8080", description = "Local Development Server URL"),
	@Server(url = "https://api.daehaengsa.kro.kr", description = "Production Server URL")
})
public class OpenApiConfig {

	public static final String BEARER_SCHEME = "Bearer";
	public static final String JWT_FORMAT = "JWT";
	private static final String JWT_AUTH = "JWT Auth";

	@Bean
	public OpenAPI openApi() {
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(JWT_AUTH);
		Components components = new Components().addSecuritySchemes(JWT_AUTH, createApiSecurityScheme());
		return new OpenAPI()
			.info(createApiInfo())
			.addSecurityItem(securityRequirement)
			.components(components);
	}

	private SecurityScheme createApiSecurityScheme() {
		return new SecurityScheme()
			.type(SecurityScheme.Type.HTTP)
			.in(SecurityScheme.In.HEADER)
			.name(JWT_AUTH)
			.scheme(BEARER_SCHEME)
			.bearerFormat(JWT_FORMAT);
	}

	private Info createApiInfo() {
		return new Info()
			.version("v0.0.1")
			.title("대행사 API Documentation")
			.description("EFUB 3rd 토이프로젝트 : 대행사(DHS) API 공식 문서입니다.");
	}
}
