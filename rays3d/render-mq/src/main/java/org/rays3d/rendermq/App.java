package org.rays3d.rendermq;

import java.util.Collection;
import java.util.List;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
	public Logger camelDefaultLogger() {

		return LoggerFactory.getLogger("org.rays3d.rendermq");
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

		//@formatter:off
		return builder
					.rootUri(renderDbUrl)
					.requestFactory(httpRequestFactory())
					.build();
		//@formatter:on
	}

	@ConditionalOnProperty(name = { "rays3d.rendermq.use-embedded-broker" }, havingValue = "true")
	@Bean(initMethod = "start", destroyMethod = "stop")
	public BrokerService brokerService(
			@Value("${rays3d.rendermq.embedded-broker-connectors}") Collection<String> connectorURLs) throws Exception {

		final BrokerService broker = new BrokerService();
		for (String connectorURL : connectorURLs)
			broker.addConnector(connectorURL);

		broker.setPersistent(false);

		return broker;
	}

	@Bean
	public ActiveMQConnectionFactory activeMQConnectionFactory(@Value("${spring.activemq.broker-url}") String brokerURL,
			@Value("${spring.activemq.user}") String brokerUserName,
			@Value("${spring.activemq.password}") String brokerPassword,
			@Value("${spring.activemq.packages.trusted}") List<String> trustedPackages) {

		final ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(brokerURL);
		connectionFactory.setUserName(brokerUserName);
		connectionFactory.setPassword(brokerPassword);
		connectionFactory.setTrustedPackages(trustedPackages);

		return connectionFactory;
	}

	@Bean(name = "activemq", initMethod = "start", destroyMethod = "stop")
	public ActiveMQComponent activeMqComponent(ConnectionFactory connectionFactory) {

		final ActiveMQComponent activemq = new ActiveMQComponent();
		activemq.setConnectionFactory(connectionFactory);

		return activemq;
	}

}
