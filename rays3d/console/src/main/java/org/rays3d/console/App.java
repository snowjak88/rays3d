package org.rays3d.console;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class App {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

	@Bean
	public HttpComponentsClientHttpRequestFactory httpRequestFactory() {

		return new HttpComponentsClientHttpRequestFactory();
	}

	@Bean
	public ConversionService conversionService() {

		return new DefaultConversionService();
	}

	@Bean("renderDbRestTemplate")
	public RestTemplate renderDbRestTemplate(@Value("${org.rays3d.renderdb.rest.url}") String renderDbUrl,
			RestTemplateBuilder builder) {

		//@formatter:off
		return builder
					.rootUri(renderDbUrl)
					.requestFactory(httpRequestFactory())
					.build();
		//@formatter:on
	}

}
