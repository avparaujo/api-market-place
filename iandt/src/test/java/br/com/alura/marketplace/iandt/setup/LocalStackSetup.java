package br.com.alura.marketplace.iandt.setup;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;

import static org.testcontainers.containers.localstack.LocalStackContainer.Service.S3;

public interface LocalStackSetup {

    LocalStackContainer LOCAL_STACK = new LocalStackContainer(DockerImageName.parse("localstack/localstack:3.5.0"))
            .withServices(S3);

    @DynamicPropertySource
    static void localstackDynamicPropertySource(DynamicPropertyRegistry registry) {
        registry.add("spring.cloud.aws.region.static", LOCAL_STACK::getRegion);
        registry.add("spring.cloud.aws.credentials.access-key", LOCAL_STACK::getAccessKey);
        registry.add("spring.cloud.aws.credentials.secret-key", LOCAL_STACK::getSecretKey);
        registry.add("spring.cloud.aws.s3.endpoint", () -> LOCAL_STACK.getEndpointOverride(S3));
    }

    @BeforeAll
    static void localstackBeforeAll() {
        LOCAL_STACK.start();
    }
}
