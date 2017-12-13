package org.rays3d.sampler;

import javax.jms.ConnectionFactory;

import org.apache.activemq.camel.component.ActiveMQComponent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class App {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

	@Bean("activemq")
	public ActiveMQComponent activeMqComponent(ConnectionFactory connectionFactory) {

		final ActiveMQComponent activemq = new ActiveMQComponent();
		activemq.setConnectionFactory(connectionFactory);

		return activemq;
	}

}
