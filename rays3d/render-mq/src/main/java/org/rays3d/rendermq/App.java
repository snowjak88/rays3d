package org.rays3d.rendermq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerPlugin;
import org.apache.activemq.broker.BrokerService;
import org.apache.activemq.camel.component.ActiveMQComponent;
import org.apache.activemq.security.SimpleAuthenticationPlugin;
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
			@Value("${rays3d.rendermq.embedded-broker-connectors}") Collection<String> connectorURLs,
			@Value("${rays3d.rendermq.embedded-username}") String username,
			@Value("${rays3d.rendermq.embedded-password}") String password) throws Exception {

		final BrokerService broker = new BrokerService();
		for (String connectorURL : connectorURLs)
			broker.addConnector(connectorURL);

		broker.setPersistent(false);

		final Map<String, String> userPasswords = new HashMap<>();
		userPasswords.put(username, password);

		final SimpleAuthenticationPlugin auth = new SimpleAuthenticationPlugin();
		auth.setUserPasswords(userPasswords);

		BrokerPlugin[] existingPlugins = broker.getPlugins();
		final List<BrokerPlugin> plugins = new ArrayList<>();

		if (existingPlugins != null)
			plugins.addAll(Arrays.asList(existingPlugins));

		plugins.add(auth);
		broker.setPlugins(plugins.toArray(new BrokerPlugin[0]));

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
