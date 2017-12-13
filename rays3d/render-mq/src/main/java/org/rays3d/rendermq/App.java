package org.rays3d.rendermq;

import javax.jms.ConnectionFactory;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.hateoas.config.EnableHypermediaSupport;
import org.springframework.hateoas.config.EnableHypermediaSupport.HypermediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableHypermediaSupport(type = { HypermediaType.HAL })
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
	public RestTemplate renderDbRestTemplate(@Value("${rays3d.renderdb.rest.url}") String renderDbUrl,
			RestTemplateBuilder builder) {

		return builder.rootUri(renderDbUrl).requestFactory(httpRequestFactory()).build();
	}

	@Bean("activemq")
	public ActiveMQComponent activeMqComponent(ConnectionFactory connectionFactory) {

		final ActiveMQComponent activemq = new ActiveMQComponent();
		activemq.setConnectionFactory(connectionFactory);

		return activemq;
	}

}
