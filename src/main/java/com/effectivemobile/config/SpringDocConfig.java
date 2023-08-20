package com.effectivemobile.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(info = @Info(title = "Effective Mobile Social Media System API", version = "v3",
				contact = @Contact(name = "(send offer)", email = "antroverden@gmail.com")))
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "jwt", paramName = "Authorization", in = SecuritySchemeIn.HEADER, scheme = "bearer" )
public class SpringDocConfig {
}