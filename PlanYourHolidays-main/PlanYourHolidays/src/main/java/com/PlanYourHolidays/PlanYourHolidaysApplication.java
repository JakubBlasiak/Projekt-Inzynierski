package com.PlanYourHolidays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class PlanYourHolidaysApplication implements RepositoryRestConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(PlanYourHolidaysApplication.class, args);
	}

	@Bean
	protected Validator validator() {
		return new LocalValidatorFactoryBean();
	}
}
