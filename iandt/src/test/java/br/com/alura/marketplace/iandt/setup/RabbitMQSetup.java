package br.com.alura.marketplace.iandt.setup;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.rabbitmq.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public interface RabbitMQSetup {

    RabbitMQContainer RABBITMQ =  new RabbitMQContainer(DockerImageName.parse("rabbitmq:3.7.25-management-alpine"));

    @DynamicPropertySource
    static void rabbitmqDynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.rabbitmq.host", RABBITMQ::getHost);
        registry.add("spring.rabbitmq.port", RABBITMQ::getAmqpPort);
        registry.add("spring.rabbitmq.username", RABBITMQ::getAdminUsername);
        registry.add("spring.rabbitmq.password", RABBITMQ::getAdminPassword);
    }

    @BeforeAll
    static void rabbitmqBeforeAll() {
        RABBITMQ.start();
    }
}
