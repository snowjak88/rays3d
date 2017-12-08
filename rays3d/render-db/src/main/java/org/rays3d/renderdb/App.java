package org.rays3d.renderdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

@SpringBootApplication
public class App {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

	@SuppressWarnings("unchecked")
	@Bean
	public Jackson2ObjectMapperBuilder configureObjectMapper() {

		return new Jackson2ObjectMapperBuilder().modulesToInstall(Hibernate4Module.class);
	}

}
