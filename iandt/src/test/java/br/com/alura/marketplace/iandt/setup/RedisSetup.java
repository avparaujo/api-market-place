package br.com.alura.marketplace.iandt.setup;

import com.redis.testcontainers.RedisContainer;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

public interface RedisSetup {

    RedisContainer REDIS = new RedisContainer(DockerImageName.parse("redis:6.2.6"));

    @DynamicPropertySource
    static void redisDynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.data.redis.host", REDIS::getRedisHost);
        registry.add("spring.data.redis.port", REDIS::getRedisPort);
    }

    @BeforeAll
    static void redisBeforeAll() {
        REDIS.start();
    }
}
